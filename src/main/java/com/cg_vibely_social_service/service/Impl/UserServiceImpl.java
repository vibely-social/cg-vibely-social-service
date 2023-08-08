package com.cg_vibely_social_service.service.Impl;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(Long id, User user) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void remove(Long id) throws EntityNotFoundException {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username");
        }

        return user.get();
    }
}
