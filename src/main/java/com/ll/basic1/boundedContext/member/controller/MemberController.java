package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rsData.RsData;
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
    public RsData login(HttpServletRequest req, HttpServletResponse resp) {
        this.username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username을 입력해주세요.");
        } else if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password 입력해주세요.");
        }
        resp.addCookie(new Cookie("isLogin", "true"));
        return memberService.tryLogin(username, password);
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData isLogin(HttpServletRequest req, HttpServletResponse resp) {
        String isLogin = "false";
        if (req.getCookies() != null) {
            isLogin = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("isLogin"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse("false");
        }
        return memberService.isLogin(isLogin, this.username);
    }
}