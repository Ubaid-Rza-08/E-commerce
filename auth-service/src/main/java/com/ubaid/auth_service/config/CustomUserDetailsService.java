package com.ubaid.auth_service.config;

import com.ubaid.auth_service.entity.UserEntity;
import com.ubaid.auth_service.repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserEntityRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity=repository.findByEmail(email).orElseThrow(()->
                new RuntimeException("User Not Found with name :"+email)
        );
        return new CustomUserDetails(userEntity);
    }
}
