package com.chuan.on_my_way.dto;


import com.chuan.on_my_way.entity.Dish;
import com.chuan.on_my_way.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
