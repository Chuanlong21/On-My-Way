package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.User;
import com.chuan.on_my_way.mapper.UserMapper;
import com.chuan.on_my_way.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
}
