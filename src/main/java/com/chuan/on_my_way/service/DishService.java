package com.chuan.on_my_way.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuan.on_my_way.dto.DishDto;
import com.chuan.on_my_way.entity.Dish;


public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id );

    public void updateWithFlavor(DishDto dishDto);

}
