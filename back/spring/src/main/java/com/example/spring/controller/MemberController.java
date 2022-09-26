package com.example.spring.controller;

import com.example.spring.domain.Member;
import com.example.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("loginApi")
    @ResponseBody
    public String create(@RequestParam("name") String name, @RequestParam("password") String password){
        Member member = new Member();
        member.setName(name);
        member.setPassword(password);

        memberService.join(member);

        return "성공";
    }

}
