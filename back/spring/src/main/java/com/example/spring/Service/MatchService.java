package com.example.spring.Service;

import com.example.spring.DTO.MatchingDTO;
import com.example.spring.DTO.OpenMatchDTO;
import com.example.spring.Repository.MatchRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    //매칭 생성
    public OpenMatchDTO create(OpenMatchDTO openMatchDTO){
        return matchRepository.save(openMatchDTO);
    }

    //오픈 매치 리스트, 종목 구분없이 다 가져옴
    public List<OpenMatchDTO> findAll(){
        return matchRepository.findAll();
    }
    //오픈 매치 리스트 필터링 해서 특정 종목만 가져오기
    public List<OpenMatchDTO> findAll(String sportType){
        return matchRepository.findAll(sportType);
    }

    //유저가 참가중인 모든 오픈 매치를 가져와서 리턴
    public List<OpenMatchDTO> findAllOpenMatchListByUserId(Long userId){
        List<OpenMatchDTO> result = new ArrayList<>();
        List<MatchingDTO> tmp = matchRepository.findAllMatchingByUserId(userId);
        tmp.stream().forEach(matchingDTO -> {
            result.add(matchRepository.findById(matchingDTO.getOpenMatchId()).get());
        });

        return result;
    }

    //매칭 삭제
    public boolean delete(Long id){
        Optional<OpenMatchDTO> optionalOpenMatchDTO = matchRepository.findById(id);
        if(optionalOpenMatchDTO.isPresent()) {
           return matchRepository.delete(optionalOpenMatchDTO.get());
        }
        return false;
    }

    //매칭 참가
    public Long joinMatch(MatchingDTO matchingDTO){
        return matchRepository.saveMatchingInfo(matchingDTO);
    }

    //매칭 취소
    public boolean leaveMatch(Long id){
        Optional<MatchingDTO> optionalMatchingDTO = matchRepository.findMatchingById(id);
        if(optionalMatchingDTO.isPresent()) {
            return matchRepository.deleteMatching(optionalMatchingDTO.get());
        }
        return false;
    }
}
