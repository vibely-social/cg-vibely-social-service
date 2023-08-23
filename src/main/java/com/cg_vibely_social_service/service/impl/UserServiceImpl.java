package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.configuration.security.JwtTokenProvider;
import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Friend;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.request.UserInfoRequestDto;
import com.cg_vibely_social_service.payload.request.UserLoginRequestDto;
import com.cg_vibely_social_service.payload.request.UserRegisterRequestDto;
import com.cg_vibely_social_service.payload.response.UserInfoResponseDto;
import com.cg_vibely_social_service.payload.response.UserLoginResponseDto;
import com.cg_vibely_social_service.payload.response.UserSuggestionResponseDto;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Converter<UserRegisterRequestDto, User> converter;
    private final Converter<UserSuggestionResponseDto, User> suggestionFriendConverter;

    private final Converter<UserInfoResponseDto, User> userInfoResponseConverter;

    private final Converter<UserInfoRequestDto, User> userInfoRequestConverter;

    @Override
    public UserImpl getCurrentUser() {
        try {
            return (UserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }

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


    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return user.get();
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = loadUserByEmail(userLoginRequestDto.getEmail());

        String token = jwtUtil.generateToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);
        return UserLoginResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar("https://media.discordapp.net/attachments/1006048991043145829/1006049027734913075/unknown.png?width=662&height=662")
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public String refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return jwtUtil.generateToken(authentication);
    }

    @Override
    public boolean checkValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isEmpty();
    }

    @Override
    public List<UserSuggestionResponseDto> find20UsersSuggestionByUserId(Long userId) {
        List<User> suggestionFriends = userRepository.find20UsersSuggestionByUserId(userId, Pageable.ofSize(20));
        List<UserSuggestionResponseDto> userSuggestionResponseDtos = suggestionFriendConverter.revert(suggestionFriends);

        List<Long> user1FriendIds = userRepository.findById(userId).orElse(null).getFriendList().stream().map(Friend::getFriendId).collect(Collectors.toList());

        for (UserSuggestionResponseDto dto : userSuggestionResponseDtos) {
            User user = userRepository.findById(dto.getId()).orElse(null);
            if (user != null) {
                List<Long> user2FriendIds = user.getFriendList().stream().map(Friend::getFriendId).collect(Collectors.toList());
                int mutualFriends = (int) user1FriendIds.stream().filter(user2FriendIds::contains).count();
                dto.setNumberMutualFriend(mutualFriends);
            }
        }
        return userSuggestionResponseDtos;
    }

    public UserPrincipal getUserPrincipal(String email) {
        System.out.println("calling getUserPrincipal");
        User user = userRepository.findByEmail(email).orElseThrow();
        return UserPrincipal.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    private boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }

    @Override
    public UserInfoResponseDto getUserInfoById(Long userId) {
        return userInfoResponseConverter.revert(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public void updateUserInfo(UserInfoRequestDto userInfoRequestDto) {
        User newInfo = userInfoRequestConverter.convert(userInfoRequestDto);
        newInfo.setPassword(userRepository.findById(newInfo.getId()).orElseThrow().getPassword());
        userRepository.save(newInfo);
    }
}
