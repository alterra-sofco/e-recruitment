package com.erecruitment.dtos.response.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtAuthenticationResponseDTO {
    private final String accessToken;

    private String tokenType = "Bearer";
}
