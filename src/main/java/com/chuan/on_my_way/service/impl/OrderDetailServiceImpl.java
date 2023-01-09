package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.Employee;
import com.chuan.on_my_way.entity.OrderDetail;
import com.chuan.on_my_way.mapper.EmployeeMapper;
import com.chuan.on_my_way.mapper.OrderDetailMapper;
import com.chuan.on_my_way.service.EmployeeService;
import com.chuan.on_my_way.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
        implements OrderDetailService {
}
