package com.chuan.on_my_way;

import com.chuan.on_my_way.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnMyWayApplicationTests {

    @Autowired
    private MailService mailService;
    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        mailService.sendSimpleMail("chuanlong63@gmail.com","SpringBoot Email","testing..");
    }
}
