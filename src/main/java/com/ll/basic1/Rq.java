package com.ll.basic1;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class Rq {
    HttpServletRequest req;
    HttpServletResponse resp;

    public void setCookie(String name, Object value) {
        resp.addCookie(new Cookie(name, value + ""));
    }

    public String getCookie(String name, String defaultValue) {
        String value = "";
        if (req.getCookies() != null) {
            value = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))
                    .findFirst()
                    .map(cookie -> cookie.getValue())
                    .orElse(defaultValue);
        }
        return value;
    }
    public long getCookieAsLong(String name, long defaultValue) {
        long value = 0;
        if (req.getCookies() != null) {
            value = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))
                    .findFirst()
                    .map(cookie -> Long.parseLong(cookie.getValue()))
                    .orElse(defaultValue);
        }
        return value;
    }

    public void removeCookie(String name) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(e -> e.getName().equals(name))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
    }

}
