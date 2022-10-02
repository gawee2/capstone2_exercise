package com.example.spring.Repository;

import com.example.spring.DTO.Member;
import com.example.spring.auth.RefreshToken;
import com.example.spring.auth.UserDetailsImpl;
import com.example.spring.auth.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


public class JpaAuthRepository implements AuthRepository{

    private final EntityManager em;

    public JpaAuthRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public UserDetailsImpl findByUserId(String userId) {
//        UserDetailsImpl userDetails = em.find(UserDetailsImpl.class, userId);

        List<Member> result = em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
        Member member = result.get(0);
        UserDetailsImpl userDetails = new UserDetailsImpl();

        userDetails.setName(member.getUserId());
        userDetails.setPassword(member.getUserPw());
        userDetails.setIdx(member.getId());
        userDetails.setEmail(member.getEmail());
        userDetails.setRole("ROLE_USER");

        return userDetails;
    }

    @Override
    public String findRefreshTokenByIdx(long idx) {
        RefreshToken refreshToken = em.find(RefreshToken.class, idx);
        return refreshToken.getRefreshToken();
    }

    @Override
    public void insertOrUpdateRefreshToken(RefreshToken refreshToken) {
        em.persist(refreshToken);
    }
}