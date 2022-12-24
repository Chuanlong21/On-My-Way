package com.chuan.on_my_way.dto;

import com.chuan.on_my_way.entity.Setmeal;

import com.chuan.on_my_way.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
