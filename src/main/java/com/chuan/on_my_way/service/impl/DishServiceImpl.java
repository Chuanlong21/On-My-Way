package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.Dish;
import com.chuan.on_my_way.entity.Employee;
import com.chuan.on_my_way.mapper.DishMapper;
import com.chuan.on_my_way.mapper.EmployeeMapper;
import com.chuan.on_my_way.service.DishService;
import com.chuan.on_my_way.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {
}
