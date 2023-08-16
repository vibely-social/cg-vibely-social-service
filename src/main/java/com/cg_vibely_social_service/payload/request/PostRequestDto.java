package com.cg_vibely_social_service.payload.request;

import com.cg_vibely_social_service.utils.PrivacyName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequestDto {

    private Long id;
    private Long userId;
    @NotNull
    private PrivacyName privacy;
    @NotBlank
    private String textContent;
    private Boolean edited;

    private List<Long> users;

}
