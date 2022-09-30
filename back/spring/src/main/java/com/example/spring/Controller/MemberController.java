package com.example.spring.Controller;

import com.example.spring.DTO.Member;
import com.example.spring.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }



    //클라이언트에서 이미 해싱한 비밀번호가 넘어왔다고 가정
    @PostMapping("/signUp")
    public Member signUp(@RequestBody Member member){
        Member newMember = new Member();
        newMember.setUserId(member.getUserId());
        newMember.setUserPw(member.getUserPw());
        newMember.setEmail(member.getEmail());


        System.out.println("id:" + newMember.getUserId());
        System.out.println("pw:" + newMember.getUserPw());
        System.out.println("em:" + newMember.getEmail());

        memberService.join(newMember);

        return newMember;
    }

    @PostMapping("/signIn")
    public boolean signIn(@RequestBody Member member){
        Member checkMember = new Member();
        checkMember.setUserId(member.getUserId());
        checkMember.setUserPw(member.getUserPw());

        //로그인 시도시 회원인지 아닌지 판단
        if(memberService.isMember(checkMember)){
            return true;
        }else{
            return false;
        }
    }

    @PostMapping("/forgetPassword")
    public boolean forgetPassword(@RequestBody Member member){
        //이메일 정보 받아서 내부적으로 인증코드 이메일 발송해줌
        System.out.println(member.getEmail());
        return true;
    }


    //여기는 잠깐 테스트용
    @GetMapping("/userList")
    public List<Member> getUserList(){
        return memberService.findMembers();
    }


}
