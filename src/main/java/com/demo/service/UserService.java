package com.demo.service;

import com.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<User> {

}
