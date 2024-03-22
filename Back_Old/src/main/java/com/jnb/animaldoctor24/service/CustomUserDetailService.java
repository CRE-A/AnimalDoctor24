package com.jnb.animaldoctor24.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("jnb".equals(username)){
            return new User("jnb","$2a$10$VKu6eW.2pHLJn3yeW0eMxuEUBxXCq/b2Vo3HwSqROGI2mmYRnXqpm",new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User not found with usernae:"+username);
        }
    }
}
