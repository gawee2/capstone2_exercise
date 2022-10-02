package com.example.spring.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String userId;
    private String accessToken;
    private String refreshToken;
    private String refreshTokenExpirationAt;

    @Builder
    public RefreshToken(long idx, String userId, String accessToken, String refreshToken,
                        String refreshTokenExpirationAt) {
        this.idx = idx;
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationAt = refreshTokenExpirationAt;
    }


    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenExpirationAt() {
        return refreshTokenExpirationAt;
    }

    public void setRefreshTokenExpirationAt(String refreshTokenExpirationAt) {
        this.refreshTokenExpirationAt = refreshTokenExpirationAt;
    }
}
