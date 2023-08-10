package com.cg_vibely_social_service.service.Impl;

import com.cg_vibely_social_service.dto.converter.UserConverter;
import com.cg_vibely_social_service.dto.request.RequestLoginDto;
import com.cg_vibely_social_service.dto.request.RequestRegisterDto;
import com.cg_vibely_social_service.dto.response.ResponseLoginDto;
import com.cg_vibely_social_service.dto.response.ResponseRegisterDto;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.security.JwtUtil;
import com.cg_vibely_social_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${user.email.regex}")
    private String emailRegex;

    @Value("${user.password.regex}")
    private String passwordRegex;

    @Value("${user.gender.regex}")
    private String genderRegex;

    @Value("${date.pattern}")
    private String datePattern;

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

    @Override
    public ResponseLoginDto authenticate(RequestLoginDto requestLoginDto) {
        ResponseLoginDto failLoginResponse = ResponseLoginDto.builder()
                .message("Invalid credential")
                .status(false)
                .build();
        try {
            User user = (User) loadUserByUsername(requestLoginDto.getEmail());

            if (checkPassword(user, requestLoginDto.getPassword())) {
                String token = jwtUtil.generateToken(user);
                ResponseLoginDto responseLoginDto = ResponseLoginDto.builder()
                        .message("Login successfully")
                        .status(true)
                        .email(user.getEmail())
                        .accessToken(token)
                        .build();
                return responseLoginDto;
            }
        } catch (UsernameNotFoundException exception) {
            return failLoginResponse;
        }

        return failLoginResponse;
    }

    @Override
    public ResponseRegisterDto register(RequestRegisterDto requestRegisterDto) {
        if (checkValidInfo(requestRegisterDto)) {
            User newUser = userConverter.registerDtoToUser(requestRegisterDto);
            User checkUser = userRepository.save(newUser);

            if (checkUser != null) {
                return ResponseRegisterDto.builder()
                        .message("Register successfully")
                        .status(true)
                        .email(requestRegisterDto.getEmail())
                        .build();
            }
        }

        return ResponseRegisterDto.builder()
                .message("Invalid info")
                .status(false)
                .build();
    }

    private boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }

    private boolean checkValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean checkValidInfo(RequestRegisterDto requestRegisterDto) {
        try {
            Date birthday = new SimpleDateFormat(datePattern).parse(requestRegisterDto.getDayOfBirth());

            return (Pattern.matches(emailRegex, requestRegisterDto.getEmail())
                    && Pattern.matches(passwordRegex, requestRegisterDto.getPassword())
                    && Pattern.matches(genderRegex, requestRegisterDto.getGender())
                    && birthday.before(new Date())
                    && checkValidEmail(requestRegisterDto.getEmail()));

        } catch (ParseException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
