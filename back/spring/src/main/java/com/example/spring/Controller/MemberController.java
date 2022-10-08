package com.example.spring.Controller;

import com.example.spring.DTO.AuthDTO;
import com.example.spring.DTO.Member;
import com.example.spring.DTO.Profile;
import com.example.spring.Service.MemberService;
import com.example.spring.auth.ApiResponse;
import com.example.spring.auth.AuthService;
import com.example.spring.auth.ResponseMap;
import lombok.experimental.PackagePrivate;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                System.out.println("중복회원이라 false 리턴");
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

        // 내부적으로 검색 위해서 토큰 날아오는거 앞에 bearer 뗌
//        String[] result = header.get("token").toString().split(" ");
//        System.out.println("검색하려는 토큰: " + result[1]);

        //현재 프로필 수정으로 요청한 유저
        String requestUser = authService.findUserByToken(header.get("token"));
        //프로필 수정을 당하는 유저
        String willChangeUser = profile.getUserId();

        System.out.println("요청 유저: " + requestUser + ", 수정 유저: " + willChangeUser);
        //자기꺼만 바꿀수 있음
        if(requestUser.equals(willChangeUser)){
            System.out.println("프로필 업데이트 됨");
            memberService.setOrUpdateProfile(profile);

            return true;
        }
        System.out.println("프로필 업데이트 실패");
        return false;
    }

    @GetMapping("/getUserProfile/{nickname}")
    public Profile getUserProfile(@PathVariable String nickname){
        return memberService.findProfileByNickname(nickname);
    }

    @GetMapping("/getProfileImg/{imgPath}")
    public MultipartFile getProfileImg(@PathVariable String imgPath){
        return null;
    }

    @PostMapping("/upload/image")
    public ApiResponse uploadsProfileImg(@RequestParam(name="image") MultipartFile image) throws IOException {

        String fileName = UUID.randomUUID().toString();
        String absolutePath = new File("/Users/duskite/downloads/img").getAbsolutePath()
                + "/" + fileName + ".jpg";

        System.out.println(absolutePath);

        ResponseMap result = new ResponseMap();

        if(!image.isEmpty()){
            File file = new File(absolutePath);
            if(!file.exists()){
                file.mkdirs();
            }

            image.transferTo(file);
            result.setResponseData("image", absolutePath);

            System.out.println("이미지 업로드 완료");
        }
        return result;
    }

}
