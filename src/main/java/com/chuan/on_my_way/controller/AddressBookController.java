package com.chuan.on_my_way.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuan.on_my_way.entity.AddressBook;
import com.chuan.on_my_way.service.AddressBookService;
import com.chuan.on_my_way.utility.BaseContext;
import com.chuan.on_my_way.utility.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrent());
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrent());
        wrapper.set(AddressBook::getIsDefault, 0);
        addressBookService.update(wrapper);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    @GetMapping("default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrent());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        AddressBook one = addressBookService.getOne(queryWrapper);
        if (null == one){
            return R.error("Found nothing ~");
        }else return R.success(one);
    }

    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrent());
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook:: getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> list = addressBookService.list(queryWrapper);
        return R.success(list);
    }
}
