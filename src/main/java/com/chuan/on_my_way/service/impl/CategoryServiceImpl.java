package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.Category;
import com.chuan.on_my_way.entity.Dish;
import com.chuan.on_my_way.entity.Setmeal;
import com.chuan.on_my_way.mapper.CategoryMapper;
import com.chuan.on_my_way.service.CategoryService;
import com.chuan.on_my_way.service.DishService;
import com.chuan.on_my_way.service.SetmealService;
import com.chuan.on_my_way.utility.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper= new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0){
            throw new CustomException("the category contains dishes");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal:: getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1>0){
            throw new CustomException("the category contains meals");
        }

        super.removeById(id);
    }
}
