package com.ll.basic1.base.rq;

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
                    .map(Cookie::getValue)
                    .orElse(defaultValue);
        }
        return value;
    }
    public long getCookieAsLong(String name, long defaultValue) {
        String value = getCookie(name, null);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean removeCookie(String name) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(e -> e.getName().equals(name))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
            return Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))
                    .count() > 0;
        }
        return false;
    }
}
