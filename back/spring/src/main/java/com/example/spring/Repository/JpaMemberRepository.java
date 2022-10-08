package com.example.spring.Repository;

import com.example.spring.DTO.Member;
import com.example.spring.DTO.Profile;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }


    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        List<Member> result = em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();

        return result.stream().findAny();
    }
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public Profile setOrUpdateProfile(Profile profile, boolean exist) {

        if(exist){
            Profile updateProfile = em.find(Profile.class, profile.getIdx());
            updateProfile.setNickname(profile.getNickname());
            updateProfile.setIntroduce(profile.getIntroduce());
            updateProfile.setImage(profile.getImage());
            em.flush();
        }else {
            em.persist(profile);
        }
        return profile;
    }

    @Override
    public Optional<Profile> findProfileByUserId(String userId) {
        List<Profile> result = em.createQuery("select m from Profile m where m.userId = :userId", Profile.class)
                .setParameter("userId", userId)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Profile> findProfileByNickname(String nickname) {

        List<Profile> result = em.createQuery("select m from Profile m where m.nickname = :nickname", Profile.class)
                .setParameter("nickname", nickname)
                .getResultList();
        return result.stream().findAny();
    }

}
