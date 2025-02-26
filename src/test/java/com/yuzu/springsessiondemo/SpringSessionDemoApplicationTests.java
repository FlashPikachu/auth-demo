package com.yuzu.springsessiondemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringSessionDemoApplicationTests {

    @Test
    void contextLoads() {
        String pass = "yuzupass";
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(10);
        String hashPass = bcryptPasswordEncoder.encode(pass);
        System.out.println(hashPass);
    }

}
