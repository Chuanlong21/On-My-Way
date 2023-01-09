package com.chuan.on_my_way.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuan.on_my_way.entity.AddressBook;
import com.chuan.on_my_way.mapper.AddressBookMapper;

import com.chuan.on_my_way.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
