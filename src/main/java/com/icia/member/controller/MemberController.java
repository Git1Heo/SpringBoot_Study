package com.icia.member.controller;


import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final 붙은것만 생성자
public class MemberController {

    private final MemberService ms;

    // 상세조회
    // /member/2, /member/3, => /member/{memberId}
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model){
        System.out.println("memberId = " + memberId);
        MemberDetailDTO member=ms.findById(memberId);
        model.addAttribute("member",member);
        return "member/detail";
    }

    //회원가입 처리
    @PostMapping("save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult){
        System.out.println("memberController.save");
        System.out.println("member = " + memberSaveDTO);

        if(bindingResult.hasErrors()){
            return "member/save";
        }

        try {
        ms.save(memberSaveDTO);
        }catch (IllegalStateException e){
            // e.getMessage() 에는 서비스에서 지정한 에러메시지가 담겨있음
            bindingResult.reject("emailCheck",e.getMessage());
            return "member/save";
        }

        return "redirect:/member/login";
    }

    // 회원가입 화면
    @GetMapping("save")
    public String save(Model model){
        model.addAttribute("member",new MemberSaveDTO());
        return "/member/save";
    }

    // 로그인 화면
    @GetMapping("login")
    public String login(Model model){
        model.addAttribute("login",new MemberLoginDTO());
        return "member/login";
    }

    //로그인 처리
    @PostMapping("login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult
            ,HttpSession session){
        System.out.println("memberController.login");
        System.out.println("memberLoginDTO = " + memberLoginDTO );

        if(bindingResult.hasErrors()){
            System.out.println("memberController.login 실행");
            return "member/login";
        }

        //boolean loginResult=ms.login(memberLoginDTO);
        if(ms.login(memberLoginDTO)){
            session.setAttribute("loginEmail",memberLoginDTO.getMemberEmail());
            return "redirect:/member/findAll";
        }
        else{
            bindingResult.reject("loginFail","이메일 또는 비밀번호가 틀립니다.!!");
            return "member/login";
        }

    }

    //목록출력
    @GetMapping
    public String findAll(Model model){
        List <MemberDetailDTO> memberList =ms.findAll();
        model.addAttribute("memberList",memberList);
        return "member/findAll";
    }



}


