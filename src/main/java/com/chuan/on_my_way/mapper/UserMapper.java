package com.chuan.on_my_way.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuan.on_my_way.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
