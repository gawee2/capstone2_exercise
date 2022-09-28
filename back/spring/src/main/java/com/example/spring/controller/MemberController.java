package com.example.spring.controller;

import com.example.spring.domain.Member;
import com.example.spring.service.MemberService;
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

    @PostMapping("/signUp")
    public Member create(@RequestParam("name") String name, @RequestParam("password") String password){
        Member member = new Member();
        member.setName(name);
        member.setPassword(password);

        System.out.println(">>>" + name);
        System.out.println(">>>" + password);

        memberService.join(member);

        return member;
    }

    @PostMapping("/setFav")
    public void setFav(){

    }

    @GetMapping("/userList")
    public List<Member> getAllMembers(){
        return memberService.findMembers();
    }

}
