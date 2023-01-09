package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.*;
import com.chuan.on_my_way.mapper.EmployeeMapper;
import com.chuan.on_my_way.mapper.OrderMapper;
import com.chuan.on_my_way.service.*;
import com.chuan.on_my_way.utility.BaseContext;
import com.chuan.on_my_way.utility.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders>
        implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Transactional
    public void submit(Orders orders) {
        Long current = BaseContext.getCurrent();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, current);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        if (list == null || list.size() == 0){
            throw  new CustomException("Empty Cart, Can't order");
        }

        User byId = userService.getById(current);

        Long addressBookId = orders.getAddressBookId();

        AddressBook byId1 = addressBookService.getById(addressBookId);

        if (byId1 == null){
            throw new CustomException("Address Error");
        }
        long id = IdWorker.getId();

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails= list.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(id);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        orders.setNumber(String.valueOf(id));
        orders.setId(id);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(current);
        orders.setUserName(byId.getName());
        orders.setConsignee(byId1.getConsignee());
        orders.setPhone(byId1.getPhone());
        orders.setAddress((byId1.getProvinceName() == null ? "": byId1.getProvinceName())
                + (byId1.getCityName() == null ? "" : byId1.getCityName())
                + (byId1.getDistrictName() == null ? "" : byId1.getDistrictName())
                + (byId1.getDetail()== null ? "" : byId1.getDetail()));
        this.save(orders);

        orderDetailService.saveBatch(orderDetails);

        shoppingCartService.remove(queryWrapper);

    }
}
