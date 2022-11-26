package com.example.spring.Service;

import com.example.spring.DTO.MatchingDTO;
import com.example.spring.DTO.Member;
import com.example.spring.DTO.OpenMatchDTO;
import com.example.spring.DTO.Profile;
import com.example.spring.Repository.MatchRepository;
import com.example.spring.Repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Transactional
public class MatchService {

    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;

    public MatchService(MatchRepository matchRepository, MemberRepository memberRepository) {
        this.matchRepository = matchRepository;
        this.memberRepository = memberRepository;
    }

    //오픈매치 생성
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

    //내가 생성한 모든 오픈 매치 리스트 리턴
    public List<OpenMatchDTO> findAllOpenMatchListByMe(Long userIdx){
        String userId = userIdxToUserId(userIdx);
        List<OpenMatchDTO> result = new ArrayList<>();
        List<OpenMatchDTO> tmp = matchRepository.findAll();
        tmp.stream().forEach(openMatchDTO -> {
            if(userId.equals(openMatchDTO.getOpenUserId())){
                result.add(openMatchDTO);
            }
        });

        return result;
    }

    //유저인덱스 값을 가지고 유저아이디 찾음
    public String userIdxToUserId(Long userIdx){
        Optional<Member> member = memberRepository.findById(userIdx);
        if(member.isPresent()){
            return member.get().getUserId();
        }
        return null;
    }

    //오픈매치 삭제
    public boolean delete(Long id){
        Optional<OpenMatchDTO> optionalOpenMatchDTO = matchRepository.findById(id);
        if(optionalOpenMatchDTO.isPresent()) {
           return matchRepository.delete(optionalOpenMatchDTO.get());
        }
        return false;
    }

    //매칭 참가
    public Long joinMatch(MatchingDTO matchingDTO){
        //유저가 이미 참여했던 매칭리스트 가져옴
        List<MatchingDTO> matchingDTOList = matchRepository.findAllMatchingByUserId(matchingDTO.getUserIndex());
        if(!matchingDTOList.isEmpty()){
            for(MatchingDTO matching: matchingDTOList){
                //현재 요청하는 매칭이 이미 있는 매칭일 경우 db에 반영하지 않음
                if(matchingDTO.getUserIndex() == matching.getUserIndex() && matchingDTO.getOpenMatchId() == matching.getOpenMatchId()){
                    return -1l;
                }
            }
        }

        return matchRepository.saveMatchingInfo(matchingDTO);
    }

    //오픈매치 수정
    public OpenMatchDTO update(OpenMatchDTO openMatchDTO){
        if(isExist(openMatchDTO.getId())){
            return matchRepository.update(openMatchDTO);
        }else{
            return null;
        }
    }

    //오픈매치가 있는지 확인
    public boolean isExist(Long id){
        Optional<OpenMatchDTO> tmp = matchRepository.findById(id);
        if(tmp.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    //매칭 취소
    public boolean leaveMatch(Long id){
        Optional<MatchingDTO> optionalMatchingDTO = matchRepository.findMatchingById(id);
        if(optionalMatchingDTO.isPresent()) {
            return matchRepository.deleteMatching(optionalMatchingDTO.get());
        }
        return false;
    }

    //현재 매치에 참여중인 유저 프로필들 모두 가져오기
    public List<Profile> getJoinedUserProfiles(Long openMatchId){
        List<Profile> profiles = new ArrayList<>();
        List<MatchingDTO> matchingDTOS = matchRepository.findAllMatchingByOpenMatchId(openMatchId);
        if(!matchingDTOS.isEmpty()){
            Iterator iterator = matchingDTOS.iterator();
            List<Long> userIndexList = new ArrayList<>();
            while (iterator.hasNext()){
                MatchingDTO tmp = (MatchingDTO) iterator.next();
                userIndexList.add(tmp.getUserIndex());
            }

            Iterator userIndexIter = userIndexList.listIterator();
            while (userIndexIter.hasNext()){
                Long userIndex = (Long) userIndexIter.next();
                Optional<Profile> profile = memberRepository.findProfileById(userIndex);
                if(profile.isPresent()){
                    profiles.add(profile.get());
                }
            }

        }

        return profiles;
    }
}
