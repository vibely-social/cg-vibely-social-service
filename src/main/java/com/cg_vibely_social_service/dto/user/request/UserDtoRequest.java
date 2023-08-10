package com.cg_vibely_social_service.dto.user.request;

import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    private Long id;
    private String firstName;
    private String lastName;

}
