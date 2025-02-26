package com.yuzu.springsessiondemo.service;

import com.yuzu.springsessiondemo.entity.User;
import com.yuzu.springsessiondemo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中加载用户
        log.error("@@@ Load user {} from database.", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//        user.setPassword("tttttttt");
        log.error("@@@ Find user: {}", user.toString());
        return user; // 返回User实体，它实现了UserDetails接口
    }
}
