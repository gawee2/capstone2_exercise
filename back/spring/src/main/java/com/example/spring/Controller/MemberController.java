package com.example.spring.Controller;

import com.example.spring.DTO.AuthDTO;
import com.example.spring.DTO.Member;
import com.example.spring.DTO.Profile;
import com.example.spring.Service.MemberService;
import com.example.spring.auth.ApiResponse;
import com.example.spring.auth.AuthService;
import lombok.experimental.PackagePrivate;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @Autowired
    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public boolean signUp(@RequestBody Member member){
        Member newMember = new Member();
        newMember.setUserId(member.getUserId());
        newMember.setUserPw(member.getUserPw());
        newMember.setEmail(member.getEmail());

        //체크
        System.out.println(member.getUserId());

        try{
            long checkDuplicate = memberService.join(newMember);
            if(checkDuplicate == -1L){
                return false;
            }
            return true;
        }catch (Exception e){
            System.out.println("예외발생: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/forgetPassword")
    public boolean forgetPassword(@RequestBody Member member){
        //이메일 정보 받아서 내부적으로 인증코드 이메일 발송해줌
        System.out.println(member.getEmail());
        return true;
    }

    @PostMapping("/setMyProfile")
    public boolean setMyProfile(@RequestBody Profile profile,
                                @RequestHeader Map<String, String> header){

        System.out.println("RequestBody:" + profile.getUserId());
        System.out.println("RequestBody:" + profile.getNickname());
        System.out.println("헤더:" + header.get("token"));

        //현재 프로필 수정으로 요청한 유저
        String requestUser = authService.findUserByToken(header.get("token"));
        //프로필 수정을 당하는 유저
        String willChangeUser = profile.getUserId();

        //자기꺼만 바꿀수 있음
        if(requestUser.equals(willChangeUser)){
            memberService.setProfile(profile);

            return true;
        }
        return false;
    }

    @GetMapping("/findUserProfile/{userId}")
    public Profile findUserProfile(@PathVariable String userId){
        return memberService.findProfileByUserId(userId);
    }





    @PostMapping("/info")
    public ApiResponse userInfo(@RequestParam String searchUser){
        ApiResponse apiResponse = new ApiResponse();

        return apiResponse;
    }

    @PostMapping("/info/test")
    public String test(){
        return "api에 접근 가능한 사용자";
    }

}
