package com.example.spring.Controller;


import com.example.spring.DTO.MatchingDTO;
import com.example.spring.DTO.Member;
import com.example.spring.DTO.OpenMatchDTO;
import com.example.spring.Service.MatchService;
import com.example.spring.Service.MemberService;
import com.example.spring.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchController {
    /*
        매칭 생성, 삭제, 참가 등의 처리를 지원하는 컨트롤러
    */

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    //매칭 생성
    @PostMapping("/openMatch")
    public OpenMatchDTO openMatch(@RequestBody OpenMatchDTO openMatchDTO){

        return matchService.create(openMatchDTO);
    }

    //매칭 삭제
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable String id){
        return matchService.delete(Long.valueOf(id));
    }

    //매칭 참가, 사용자id와 오픈매치id를 가져와서 매칭db에 생성함
    @PostMapping("/joinMatch")
    public Long joinMatch(@RequestBody MatchingDTO matchingDTO){
        return matchService.joinMatch(matchingDTO);
    }
    //매칭 탈퇴, 매칭 떠나기
    @DeleteMapping("/leaveMatch/{id}")
    public boolean leaveMatch(@PathVariable String id){
        return matchService.leaveMatch(Long.valueOf(id));
    }


    //오픈 매치 리스트
    @GetMapping("/openMatchList")
    public List<OpenMatchDTO> matchList(){
        return matchService.findAll();
    }
    //오픈 매치 리스트 필터
    @GetMapping("/openMatchList/{sportType}")
    public List<OpenMatchDTO> matchList(@PathVariable String sportType){
        return matchService.findAll(sportType);
    }


    //유저가 참여중인 매칭 리스트
    //여기서는 사실 참여중인 오픈 매칭 리스트를 리턴하면 될 듯
    @GetMapping("/joinedOpenMatch/{userId}")
    public List<OpenMatchDTO> matchingList(@PathVariable String userId){
        return matchService.findAllOpenMatchListByUserId(Long.valueOf(userId));
    }


}
