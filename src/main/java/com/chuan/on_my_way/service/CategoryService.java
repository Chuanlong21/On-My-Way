package com.chuan.on_my_way.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuan.on_my_way.entity.Category;


public interface CategoryService  extends IService<Category> {
    public void remove(Long id);
}
