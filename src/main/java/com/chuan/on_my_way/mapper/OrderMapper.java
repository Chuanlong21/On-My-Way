package com.chuan.on_my_way.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuan.on_my_way.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
