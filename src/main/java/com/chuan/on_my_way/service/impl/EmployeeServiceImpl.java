package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.Employee;
import com.chuan.on_my_way.mapper.EmployeeMapper;
import com.chuan.on_my_way.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
* @author chuan
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-11-18 19:37:08
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService {

}




