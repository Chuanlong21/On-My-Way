package com.chuan.on_my_way.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.chuan.on_my_way.dto.SetmealDto;
import com.chuan.on_my_way.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);
}
