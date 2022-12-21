package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.Employee;
import com.chuan.on_my_way.entity.Setmeal;
import com.chuan.on_my_way.mapper.EmployeeMapper;
import com.chuan.on_my_way.mapper.SetmealMapper;
import com.chuan.on_my_way.service.EmployeeService;
import com.chuan.on_my_way.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {
}
