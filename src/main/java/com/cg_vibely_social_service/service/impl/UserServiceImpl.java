package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.converter.impl.UserRequestDtoConverter;
import com.cg_vibely_social_service.payload.request.LoginRequestDto;
import com.cg_vibely_social_service.payload.request.RegisterRequestDto;
import com.cg_vibely_social_service.payload.response.LoginResponseDto;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.configuration.security.JwtUtil;
import com.cg_vibely_social_service.service.UserService;
import com.cg_vibely_social_service.utils.Regex;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRequestDtoConverter userRequestDtoConverter;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final Regex regex;

    private final Converter<RegisterRequestDto, User> registerConverter;


    @Override
    public void save(User user) {
        userRepository.save(user);
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
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        LoginResponseDto failLoginResponse = LoginResponseDto.builder()
                .message("Invalid credential")
                .status(false)
                .build();
        try {
            User user = (User) loadUserByUsername(loginRequestDto.getEmail());

            if (checkPassword(user, loginRequestDto.getPassword())) {
                String token = jwtUtil.generateToken(user);
                String refreshToken = jwtUtil.generateRefreshToken(user);
                LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                        .message("Login successfully")
                        .status(true)
                        .email(user.getEmail())
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build();
                return loginResponseDto;
            }
        } catch (UsernameNotFoundException exception) {
            return failLoginResponse;
        }
        return failLoginResponse;
    }

    @Override
    public LoginResponseDto refreshToken(String token) {
        LoginResponseDto failLoginResponse = LoginResponseDto.builder()
                .message("Illegal token")
                .status(false)
                .build();

        if (token.startsWith("Bearer ")) {
            String refreshToken = token.substring(7);
            String email = jwtUtil.extractEmail(refreshToken);

            User user = (User) loadUserByUsername(email);

            if (email != null && jwtUtil.isTokenValid(refreshToken)) {
                String newToken = jwtUtil.generateRefreshToken(user);
                LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                        .message("Login successfully")
                        .status(true)
                        .email(user.getEmail())
                        .accessToken(newToken)
                        .build();
                return loginResponseDto;
            }
        }
        return failLoginResponse;
    }

    @Override
    public boolean checkValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }


}
