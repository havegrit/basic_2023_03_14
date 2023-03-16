package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    public String showLogin() {
        return "usr/member/login";
    }

    @PostMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username을 입력해주세요.");
        } else if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password 입력해주세요.");
        }
        RsData rsData = memberService.tryLogin(username, password);
        if (rsData.isSuccess()) {
            long memberId = (long) rsData.getData();
            rq.setSession("loginMemberId", memberId);
        }
        return rsData;
    }

    @GetMapping("/member/me")
    public String showMe(Model model) {
        long loginMemberId = rq.getLoginMemberId();
        Member member = memberService.findById(loginMemberId);
        model.addAttribute("me", member);
        return "usr/member/me";
    }
    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
        boolean isLogout = rq.removeSession("loginMemberId");
        if (isLogout) {
            return RsData.of("S-1", "로그아웃 되었습니다.");
        }
        return RsData.of("S-2", "이미 로그아웃 되었습니다.");
    }
}