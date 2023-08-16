package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.configuration.security.JwtUtil;
import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.request.UserRegisterRequestDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.UserService;
import com.cg_vibely_social_service.utils.Regex;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final Regex regex;

    private final Converter<UserRegisterRequestDto, User> converter;


    @Override
    public void save(UserRegisterRequestDto userRegisterRequestDto) {
        userRepository.save(converter.convert(userRegisterRequestDto));
    }

    @Override
    public void update(Long id, User user) {
        Optional<User> loadUser = userRepository.findById(id);
        if (loadUser.isPresent()) {
            userRepository.save(user);
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return user.get();
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto failLoginResponse = UserLoginResponseDto.builder()
                .message("Invalid credential")
                .status(false)
                .build();
        try {
            User user = (User) loadUserByUsername(userLoginRequestDto.getEmail());

            if (checkPassword(user, userLoginRequestDto.getPassword())) {
                String token = jwtUtil.generateToken(user);
                String refreshToken = jwtUtil.generateRefreshToken(user);
                return UserLoginResponseDto.builder()
                        .message("Login successfully")
                        .status(true)
                        .id(user.getId())
                        .email(user.getEmail())
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build();
            }
        } catch (UsernameNotFoundException exception) {
            return failLoginResponse;
        }
        return failLoginResponse;
    }

    @Override
    public String refreshToken(String bearerToken) {
        if (jwtUtil.isTokenValid(bearerToken)) {
            String email = jwtUtil.extractEmail(bearerToken.substring(7));
            User user = (User) loadUserByUsername(email);
            if (user != null) {
                return jwtUtil.generateRefreshToken(user);
            }
        }
        return "error";
    }

    @Override
    public boolean checkValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isEmpty();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }

}
