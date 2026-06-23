package com.aivle.bookapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String nickname;
}
