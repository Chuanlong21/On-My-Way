package com.chuan.on_my_way.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuan.on_my_way.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chuan
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-11-18 19:37:08
* @Entity generator.domain.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




