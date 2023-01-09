package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.Setmeal;
import com.chuan.on_my_way.entity.ShoppingCart;
import com.chuan.on_my_way.mapper.SetmealMapper;
import com.chuan.on_my_way.mapper.ShoppingCartMapper;
import com.chuan.on_my_way.service.SetmealService;
import com.chuan.on_my_way.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {
}
