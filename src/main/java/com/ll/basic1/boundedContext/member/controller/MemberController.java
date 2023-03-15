package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class MemberController {
    private String username;
    private MemberService memberService;
    // 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletResponse resp) {
        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username을 입력해주세요.");
        } else if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password 입력해주세요.");
        }
        RsData rsData = memberService.tryLogin(username, password);
        if (rsData.isSuccess()) {
            long memberId = (long) rsData.getData();
            resp.addCookie(new Cookie("loginMemberId", memberId + ""));
        }
        return rsData;
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req) {
        long loginMemberId = 0;
        if (req.getCookies() != null) {
            loginMemberId = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginMemberId"))
                    .map(Cookie::getValue)
                    .mapToLong(Long::parseLong)
                    .findFirst()
                    .orElse(0);
        }
        boolean isLogin = loginMemberId > 0;
        if (!isLogin) {
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }

        Member member = memberService.findById(loginMemberId);

        return RsData.of("S-1", "당신의 username은 %s 입니다.".formatted(member.getUsername()));
    }
    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(e -> e.getName().equals("loginMemberId"))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
        return RsData.of("S-1", "로그아웃 되었습니다.");
    }
}