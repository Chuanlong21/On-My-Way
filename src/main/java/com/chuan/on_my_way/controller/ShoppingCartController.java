package com.chuan.on_my_way.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuan.on_my_way.entity.ShoppingCart;
import com.chuan.on_my_way.service.ShoppingCartService;
import com.chuan.on_my_way.utility.BaseContext;
import com.chuan.on_my_way.utility.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        Long current = BaseContext.getCurrent();
        shoppingCart.setUserId(current);
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,current);
//        queryWrapper.eq(ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());

        if (dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        if (one!=null){
            Integer number = one.getNumber();
            one.setNumber(number+1);
            shoppingCartService.updateById(one);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            one = shoppingCart;
        }

        return R.success(one);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        Long current = BaseContext.getCurrent();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,current);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        Long current = BaseContext.getCurrent();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,current);
        shoppingCartService.remove(queryWrapper);
        return R.success("Empty!");
    }

}
