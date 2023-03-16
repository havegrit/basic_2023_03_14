package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/member/doLogin")
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
    @ResponseBody
    public RsData showMe() {
        long loginMemberId = rq.getSessionAsLong("loginMemberId", 0);
        boolean isLogin = loginMemberId > 0;
        if (!isLogin) {
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }

        Member member = memberService.findById(loginMemberId);

        return RsData.of("S-1", "당신의 username은 %s 입니다.".formatted(member.getUsername()), member.getId());
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