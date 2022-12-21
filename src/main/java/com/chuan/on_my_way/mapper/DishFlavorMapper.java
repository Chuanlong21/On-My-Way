package com.chuan.on_my_way.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuan.on_my_way.entity.Dish;
import com.chuan.on_my_way.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
