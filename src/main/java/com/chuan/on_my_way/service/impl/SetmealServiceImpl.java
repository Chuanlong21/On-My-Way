package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.dto.SetmealDto;
import com.chuan.on_my_way.entity.Employee;
import com.chuan.on_my_way.entity.Setmeal;
import com.chuan.on_my_way.entity.SetmealDish;
import com.chuan.on_my_way.mapper.EmployeeMapper;
import com.chuan.on_my_way.mapper.SetmealMapper;
import com.chuan.on_my_way.service.EmployeeService;
import com.chuan.on_my_way.service.SetMealDishService;
import com.chuan.on_my_way.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {
    @Autowired
    private SetMealDishService setMealDishService;
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setMealDishService.saveBatch(setmealDishes);
    }
}
