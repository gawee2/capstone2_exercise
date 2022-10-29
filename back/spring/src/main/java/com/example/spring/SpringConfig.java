package com.example.spring;

import com.example.spring.Repository.*;
import com.example.spring.Service.EmailService;
import com.example.spring.Service.EmailServiceImpl;
import com.example.spring.Service.MatchService;
import com.example.spring.Service.MemberService;
import com.example.spring.auth.AuthService;
import com.example.spring.auth.JwtProvider;
import com.example.spring.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em = em;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new JpaMemberRepository(em);
    }

    @Bean
    public MatchService matchService(){ return new MatchService(matchRepository());}
    @Bean
    public MatchRepository matchRepository() { return new JpaMatchRepository(em);}


    @Bean
    public EmailService emailService(){ return new EmailServiceImpl(memberService());}

    @Bean
    public AuthRepository authRepository(){ return new JpaAuthRepository(em);}

    @Bean AuthenticationManager authenticationManager(){ return new AuthenticationManager() {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return null;
        }
    };}

    @Bean AuthService authService(){return new AuthService(jwtProvider(), authenticationManager(), authRepository());}
    @Bean JwtProvider jwtProvider(){return new JwtProvider(userDetailsService());}

    @Bean
    UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl(authRepository());
    }

}
