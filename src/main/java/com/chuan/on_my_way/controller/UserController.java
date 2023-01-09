package com.chuan.on_my_way.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuan.on_my_way.entity.User;
import com.chuan.on_my_way.service.MailService;
import com.chuan.on_my_way.service.UserService;
import com.chuan.on_my_way.utility.R;
import com.chuan.on_my_way.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String email = user.getEmail();
        if (StringUtils.isNotEmpty(email)){
            String  code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = " + code);
            mailService.sendSimpleMail(email,"On My Way",code);
            session.setAttribute(email,code);
            return R.success("Verify your code");
        }
        return R.error("Code send fail");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){

        String email = map.get("email").toString();
        String code = map.get("code").toString();
        Object attribute = session.getAttribute(email);
        if (attribute!= null && attribute.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail,email);
            User user = userService.getOne(queryWrapper);
            if (user == null){
                user = new User();
                user.setEmail(email);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("Code Error");


    }

}
