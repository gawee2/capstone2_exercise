package com.example.spring.Repository;

import com.example.spring.DTO.MatchingDTO;
import com.example.spring.DTO.Member;
import com.example.spring.DTO.OpenMatchDTO;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    OpenMatchDTO save(OpenMatchDTO openMatchDTO);
    Long saveMatchingInfo(MatchingDTO matchingDTO);
    List<OpenMatchDTO> findAll();
    List<OpenMatchDTO> findAll(String sportType);
//    List<OpenMatchDTO> findAllOpenMatchById(Long matchId);
    Optional<OpenMatchDTO> findById(Long matchId);

    Optional<MatchingDTO> findMatchingById(Long matchingId);
    List<MatchingDTO> findAllMatchingByUserId(Long userId);
    List<MatchingDTO> findAllMatchingByOpenMatchId(Long openMatchId);


    boolean delete(OpenMatchDTO openMatchDTO);
    boolean deleteMatching(MatchingDTO matchingDTO);

}
