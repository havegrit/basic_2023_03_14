package com.ll.basic1.boundedContext.home.controller;

import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

// @Controller 의 의미
// 개발자가 스프링부트에게 말한다.
// 아래 있는 HomeController 는 컨트롤러이다.
@Controller
public class HomeController {
    private int cnt;
    private List<Person> people;
    private int id;
    //필드 주입
    @Autowired
    private MemberService memberService;

    HomeController() {
        cnt = 0;
        people = new ArrayList<>();
        id = 1;
    }
    // @GetMapping("/home/main") 의 의미
    // 개발자가 스프링부트에게 말한다.
    // 만약에 /home/main 이런 요청이 오면 아래 메서드를 실행해줘
    @GetMapping("/home/main")
    // @ResponseBody 의 의미
    // 아래 메서드를 실행한 후 그 리턴값을 응답으로 삼아줘
    @ResponseBody
    public String showMain() {
        return "안녕하세요.";
    }
    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다.";
    }
    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "즐거웠습니다.";
    }
    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease() {
        return cnt++;
    }
    @GetMapping("/home/reqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        resp.getWriter().append("Hello you're %d years old.".formatted(age));
    }
//    @GetMapping("/home/cookie/increase")
//    @ResponseBody
//    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        int countInCookie = 0;
//        if(req.getCookies() != null){
//            countInCookie = Arrays.stream(req.getCookies())
//                    .filter(cookie -> cookie.getName().equals("count"))
//                    .map(cookie -> cookie.getValue())
//                    .mapToInt(Integer::parseInt)
//                    .findFirst()
//                    .orElse(0);
//        }
//
//        int newCountInCookie = countInCookie+1;
//        resp.addCookie(new Cookie("count", newCountInCookie + ""));
//
//        return countInCookie;
//    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) {
        int cookieInCount = 0;
        if (req.getCookies() != null) {
            cookieInCount = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }
        int newCookieIntCount = cookieInCount+1;
        resp.addCookie(new Cookie("count", newCookieIntCount + ""));
        return cookieInCount;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam int b) {
        return a+b;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(@RequestParam String name, @RequestParam int age) {
        Person newPerson = new Person(id++, name, age);
        people.add(newPerson);
        return String.format("%d번 사람이 추가되었습니다.", newPerson.getId());
    }
    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return people;
    }
    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id) {
        if(people.removeIf(v -> v.getId() == id)) {
            return String.format("%d번 사람이 삭제되었습니다.", id);
        }
        return String.format("%d번 사람이 존재하지 않습니다.", id);
    }
    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id, String name, int age) {
        Person found = people
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        found.setName(name);
        found.setAge(age);

        return "%d번 사람이 수정되었습니다.".formatted(id);
    }

    @GetMapping("/home/user1")
    @ResponseBody
    public Member showUser1() {
        return memberService.findByUsername("user1");
    }

}

@AllArgsConstructor
@Getter
@ToString
class Person {
    private final int id;
    @Setter
    private String name;
    @Setter
    private int age;
}

