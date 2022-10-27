package com.erecruitment.dtos.response.auth;

import com.erecruitment.entities.RoleName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtAuthenticationResponseDTO {
    private final String accessToken;
    private final String name;
    private final RoleName role;
    private final Long userId;
    private String tokenType = "Bearer";
}
