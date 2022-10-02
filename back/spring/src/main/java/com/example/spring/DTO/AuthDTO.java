package com.example.spring.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


public class AuthDTO {

    /**
     * 로그인 시 사용하는 DTO
     */
    @Getter
    @Setter
    public static class LoginDTO {

        @NotBlank
        private String userId;

        @NotBlank
        private String userPw;
    }

    /**
     * Refresh Token을 사용하여 새로운 Access Token을 발급받을 때 사용하는 DTO
     */
    @Getter
    @Setter
    public static class GetNewAccessTokenDTO {

        private long refreshIdx;
    }
}
