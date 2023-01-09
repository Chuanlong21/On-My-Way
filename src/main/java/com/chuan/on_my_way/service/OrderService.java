package com.chuan.on_my_way.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuan.on_my_way.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
