package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.SetmealDish;
import com.chuan.on_my_way.mapper.SetMealDishMapper;
import com.chuan.on_my_way.service.SetMealDishService;
import com.chuan.on_my_way.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealDishServiceImp extends ServiceImpl<SetMealDishMapper, SetmealDish> implements SetMealDishService {
}
