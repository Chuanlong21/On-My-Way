package com.chuan.on_my_way.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chuan.on_my_way.dto.DishDto;
import com.chuan.on_my_way.entity.Category;
import com.chuan.on_my_way.entity.Dish;
import com.chuan.on_my_way.entity.DishFlavor;
import com.chuan.on_my_way.service.CategoryService;
import com.chuan.on_my_way.service.DishFlavorService;
import com.chuan.on_my_way.service.DishService;
import com.chuan.on_my_way.utility.CustomException;
import com.chuan.on_my_way.utility.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishFlavorController {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("Add Successfully!!");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category cateName = categoryService.getById(categoryId);
            if (cateName != null){
                dishDto.setCategoryName(cateName.getName());
            }

            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("Update Successfully!");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list( Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!= null ,Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtos = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category cateName = categoryService.getById(categoryId);
            if (cateName != null){
                dishDto.setCategoryName(cateName.getName());
            }
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> list1 = dishFlavorService.list(wrapper);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());;

        return R.success(dishDtos);

    }

    @PostMapping("/status/{check}")
    public R<String> soldOut(@PathVariable int check, @RequestParam List<Long> ids){
        int status = 0;
        //停售是0，那么我们在数据库里的status就应该是1
        if (check == 0){
            status = 1;
        }
        //虽然status会返回0或者1，但是我们后端得查询数据库中是否真的停售或者开售
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,ids);
        queryWrapper.eq(Dish::getStatus,status);
        int count = dishService.count(queryWrapper);
        if (count < ids.size()){
            throw new CustomException("Can't delete, check your operation!");
        }
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getId,ids);
        List<Dish> list = dishService.list(wrapper);
        for (Dish dish:list){
            dish.setStatus(check);
        }
        dishService.saveOrUpdateBatch(list);
        return R.success("Update successfully!");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.removeWithFlavor(ids);
        return R.success("Delete Successfully!");
    }
}
