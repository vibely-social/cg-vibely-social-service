package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, GenericService<User> {

}
