package com.example.spring.Controller;

import com.example.spring.DTO.Member;
import com.example.spring.Service.MemberService;
import com.example.spring.auth.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signUp")
    public Member signUp(@RequestBody Member member){
        Member newMember = new Member();
        newMember.setUserId(member.getUserId());
        newMember.setUserPw(member.getUserPw());
        newMember.setEmail(member.getEmail());

        memberService.join(newMember);

        return newMember;
    }

    @PostMapping("/forgetPassword")
    public boolean forgetPassword(@RequestBody Member member){
        //이메일 정보 받아서 내부적으로 인증코드 이메일 발송해줌
        System.out.println(member.getEmail());
        return true;
    }

    @PostMapping("/info")
    public ApiResponse userInfo(@RequestParam String searchUser){
        ApiResponse apiResponse = new ApiResponse();

        return apiResponse;
    }


}
