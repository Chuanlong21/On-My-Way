package com.chuan.on_my_way.controller;

import com.chuan.on_my_way.dto.DishDto;
import com.chuan.on_my_way.service.DishFlavorService;
import com.chuan.on_my_way.service.DishService;
import com.chuan.on_my_way.utility.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishFlavorController {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        return null;
    }
}
