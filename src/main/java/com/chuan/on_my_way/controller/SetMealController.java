package com.chuan.on_my_way.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuan.on_my_way.dto.DishDto;
import com.chuan.on_my_way.dto.SetmealDto;
import com.chuan.on_my_way.entity.Category;
import com.chuan.on_my_way.entity.Dish;
import com.chuan.on_my_way.entity.Setmeal;
import com.chuan.on_my_way.entity.SetmealDish;
import com.chuan.on_my_way.service.CategoryService;
import com.chuan.on_my_way.service.SetMealDishService;
import com.chuan.on_my_way.service.SetmealService;
import com.chuan.on_my_way.utility.CustomException;
import com.chuan.on_my_way.utility.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetMealDishService setMealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("Add Successfully!");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> newPageInfo = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null, Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,newPageInfo,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> setmealDtos = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            if (byId!=null){
                setmealDto.setCategoryName(byId.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        newPageInfo.setRecords(setmealDtos);
        return R.success(newPageInfo);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("Delete Successfully!");
    }

    @PostMapping("/status/{check}")
    public R<String> soldOut(@PathVariable int check, @RequestParam List<Long> ids){
        int status = 0;
        //停售是0，那么我们在数据库里的status就应该是1
        if (check == 0){
            status = 1;
        }
        //虽然status会返回0或者1，但是我们后端得查询数据库中是否真的停售或者开售
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids).eq(Setmeal::getStatus,status);
        int count = setmealService.count(queryWrapper);
        if (count < ids.size()){
            throw new CustomException("Can't delete, check your operation!");
        }
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids);
        List<Setmeal> list = setmealService.list(wrapper);
        for (Setmeal setmeal:list){
            setmeal.setStatus(check);
        }
        setmealService.saveOrUpdateBatch(list);
        return R.success("Update successfully!");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto byIdWithDish = setmealService.getByIdWithDish(id);
        return R.success(byIdWithDish);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("Update Successfully!");
    }
}
