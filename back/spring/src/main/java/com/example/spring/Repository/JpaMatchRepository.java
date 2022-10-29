package com.example.spring.Repository;

import com.example.spring.DTO.MatchingDTO;
import com.example.spring.DTO.Member;
import com.example.spring.DTO.OpenMatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMatchRepository implements MatchRepository{

    private final EntityManager em;

    public JpaMatchRepository(EntityManager em) {
        this.em = em;
    }

    //매칭 생성
    @Override
    public OpenMatchDTO save(OpenMatchDTO openMatchDTO){
        em.persist(openMatchDTO);
        return openMatchDTO;
    }

    //매칭 참가
    @Override
    public Long saveMatchingInfo(MatchingDTO matchingDTO) {
        em.persist(matchingDTO);
        return matchingDTO.getId();
    }

    @Override
    public List<OpenMatchDTO> findAll() {

        return em.createQuery("select m from OpenMatchDTO m", OpenMatchDTO.class).getResultList();
    }

    @Override
    public List<OpenMatchDTO> findAll(String type) {

        return em.createQuery("select m from OpenMatchDTO m where m.sportType=:type ", OpenMatchDTO.class)
                .setParameter("type", type)
                .getResultList();
    }

//    @Override
//    public List<OpenMatchDTO> findAllOpenMatchById(Long matchId){
//        return em.createQuery("select m from OpenMatchDTO m where m.=:type ", OpenMatchDTO.class)
//                .setParameter("type", type)
//                .getResultList();
//    }

    @Override
    public Optional<OpenMatchDTO> findById(Long id) {
        OpenMatchDTO openMatchDTO = em.find(OpenMatchDTO.class, id);
        return Optional.ofNullable(openMatchDTO);
    }

    @Override
    public Optional<MatchingDTO> findMatchingById(Long matchingId) {
        MatchingDTO matchingDTO = em.find(MatchingDTO.class, matchingId);
        return Optional.ofNullable(matchingDTO);
    }

    //유저 아이디로 매칭 목록 가져옴
    @Override
    public List<MatchingDTO> findAllMatchingByUserId(Long userId) {
        return em.createQuery("select m from MatchingDTO m where m.userId=:userId ", MatchingDTO.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    //오픈 매치 아이디로 매칭 목록 가져옴
    @Override
    public List<MatchingDTO> findAllMatchingByOpenMatchId(Long openMatchId) {
        return em.createQuery("select m from MatchingDTO m where m.openMatchId=:openMatchId ", MatchingDTO.class)
                .setParameter("openMatchId", openMatchId)
                .getResultList();
    }

    @Override
    public boolean delete(OpenMatchDTO openMatchDTO) {

        try{
            em.remove(openMatchDTO);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMatching(MatchingDTO matchingDTO) {
        try{
            em.remove(matchingDTO);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
