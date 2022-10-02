package com.example.spring.Repository;

import com.example.spring.auth.RefreshToken;
import com.example.spring.auth.UserDetailsImpl;
import org.springframework.security.core.userdetails.User;

public interface AuthRepository {

    UserDetailsImpl findByUserId(String userId);
    String findRefreshTokenByIdx(long idx);
    void insertOrUpdateRefreshToken(RefreshToken refreshToken);
}
