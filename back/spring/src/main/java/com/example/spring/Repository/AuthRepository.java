package com.example.spring.Repository;

import com.example.spring.auth.RefreshToken;
import com.example.spring.auth.UserDetailsImpl;
import org.springframework.security.core.userdetails.User;

public interface AuthRepository {

    UserDetailsImpl findByUserId(String userId);

    String findRefreshTokenByIdx(long idx);
    void insertOrUpdateRefreshToken(RefreshToken refreshToken);

    boolean isExist(String userId);

    //헤더에 포함되어 온 토큰으로 사용자 판단하는 부분
    String findUserByToken(String token);

}
