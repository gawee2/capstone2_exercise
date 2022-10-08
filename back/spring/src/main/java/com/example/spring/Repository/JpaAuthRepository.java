package com.example.spring.Repository;

import com.example.spring.DTO.Member;
import com.example.spring.DTO.Profile;
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

        try{
            RefreshToken tmpRefreshToken = em.find(RefreshToken.class, refreshToken.getIdx());
            em.flush();
        }catch (Exception e){
            em.persist(refreshToken);
        }

    }

    @Override
    public boolean isExist(String userId) {

        List<RefreshToken> result = em.createQuery("select m from RefreshToken m where m.userId = :userId", RefreshToken.class)
                .setParameter("userId", userId)
                .getResultList();

        try{
            result.get(0);
            //사용자가 없을경우 예외 던져짐
        }catch (IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    //액세스 토큰을 가지고 사용자 누구인지 찾음
    @Override
    public String findUserByToken(String accessToken) {

        List<RefreshToken> result = em.createQuery("select m from RefreshToken m where m.accessToken = :accessToken", RefreshToken.class)
                .setParameter("accessToken", accessToken)
                .getResultList();

        return result.get(0).getUserId();
    }

    @Override
    public Long findTokenIdxByAccessToken(String accessToken) {
        List<RefreshToken> result = em.createQuery("select m from RefreshToken m where m.accessToken = :accessToken", RefreshToken.class)
                .setParameter("accessToken", accessToken)
                .getResultList();

        return result.get(0).getIdx();
    }
}
