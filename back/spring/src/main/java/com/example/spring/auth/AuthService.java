package com.example.spring.auth;

import com.example.spring.DTO.AuthDTO;
import com.example.spring.DTO.Member;
import com.example.spring.Repository.AuthRepository;
import com.example.spring.enumcollection.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.HashMap;
import java.util.Map;

@Transactional
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthRepository authRepository;

    @Autowired
    public AuthService(JwtProvider jwtProvider, AuthenticationManager authenticationManager,AuthRepository authRepository) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    //토큰을 가지고 userId 찾아서 리턴
    public String findUserByToken(String token){
        return authRepository.findUserByToken(token);
    }


    public ApiResponse login(AuthDTO.LoginDTO loginDTO) {
        ResponseMap result = new ResponseMap();

        try {
            System.out.println("auth service 로그인");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getUserPw())
            );

            Map createToken = createTokenReturn(loginDTO);
            result.setResponseData("accessToken", createToken.get("accessToken"));
            result.setResponseData("refreshIdx", createToken.get("refreshIdx"));
        } catch (Exception e) {
            System.out.println("auth service 로그인 오류");
            e.printStackTrace();
//            throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException);
        }
        System.out.println("auth service 로그인 문제 없음");
        return result;
    }

    public boolean isExist(Member member){
        if(authRepository.isExist(member.getUserId())){
            return true;
        }
        return false;
    }

    public ApiResponse newAccessToken(AuthDTO.GetNewAccessTokenDTO getNewAccessTokenDTO, HttpServletRequest request){
        ResponseMap result = new ResponseMap();
        String refreshToken = authRepository.findRefreshTokenByIdx(getNewAccessTokenDTO.getRefreshIdx());

        // AccessToken은 만료되었지만 RefreshToken은 만료되지 않은 경우
        if(jwtProvider.validateJwtToken(request, refreshToken)){
            String userId = jwtProvider.getUserInfo(refreshToken);
            AuthDTO.LoginDTO loginDTO = new AuthDTO.LoginDTO();
            loginDTO.setUserId(userId);

            Map createToken = createTokenReturn(loginDTO);
            result.setResponseData("accessToken", createToken.get("accessToken"));
            result.setResponseData("refreshIdx", createToken.get("refreshIdx"));
        }else{
            // RefreshToken 또한 만료된 경우는 로그인을 다시 진행해야 한다.
            result.setResponseData("code", ErrorCode.ReLogin.getCode());
            result.setResponseData("message", ErrorCode.ReLogin.getMessage());
            result.setResponseData("HttpStatus", ErrorCode.ReLogin.getStatus());
        }
        return result;
    }

    // 토큰을 생성해서 반환
    private Map<String, String> createTokenReturn(AuthDTO.LoginDTO loginDTO) {
        Map result = new HashMap();

        System.out.println("createTokenReturn");

        String accessToken = jwtProvider.createAccessToken(loginDTO);
        String refreshToken = jwtProvider.createRefreshToken(loginDTO).get("refreshToken");
        String refreshTokenExpirationAt = jwtProvider.createRefreshToken(loginDTO).get("refreshTokenExpirationAt");


        System.out.println(accessToken);
        System.out.println(refreshToken);
        System.out.println(refreshTokenExpirationAt);


        RefreshToken insertRefreshToken = RefreshToken.builder()
                .userId(loginDTO.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationAt(refreshTokenExpirationAt)
                .build();

        authRepository.insertOrUpdateRefreshToken(insertRefreshToken);

        result.put("accessToken", accessToken);
        result.put("refreshIdx", insertRefreshToken.getIdx());

        return result;
    }
}
