/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuzu.springsessiondemo.demos.web;

import com.yuzu.springsessiondemo.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("/test")
public class BasicController {

    private final UserRepository userRepository;

    @GetMapping("/test1")
    @PreAuthorize("hasRole('ADMIN')")
    public String test1() {
        return "test1";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasRole('USER')")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    @PreAuthorize("permitAll()")
    public String test3() {
        return "test3";
    }

    @GetMapping("/test4")
    public String test4() {
        return "test4";
    }

    public BasicController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get_user")
    @ResponseBody
    @Transactional
    public com.yuzu.springsessiondemo.entity.User  getUser() {
        com.yuzu.springsessiondemo.entity.User user = userRepository.findByUsername("admin").get();
        user.setPassword("123456");
        return user;
    }

    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/hello")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return "Hello " + name;
    }

    // http://127.0.0.1:8080/user
    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public User user() {
        User user = new User();
        user.setName("theonefx");
        user.setAge(666);
        return user;
    }

    // http://127.0.0.1:8080/save_user?name=newName&age=11
    @RequestMapping("/save_user")
    @ResponseBody
    public String saveUser(User u) {
        return "user will save: name=" + u.getName() + ", age=" + u.getAge();
    }

    // http://127.0.0.1:8080/html
    @RequestMapping("/html")
    public String html(){
        return "index.html";
    }

    @ModelAttribute
    public void parseUser(@RequestParam(name = "name", defaultValue = "unknown user") String name
            , @RequestParam(name = "age", defaultValue = "12") Integer age, User user) {
        user.setName("zhangsan");
        user.setAge(18);
    }
}
