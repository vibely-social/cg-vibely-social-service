package com.vibely_social.service;

import com.vibely_social.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<User> {

}
