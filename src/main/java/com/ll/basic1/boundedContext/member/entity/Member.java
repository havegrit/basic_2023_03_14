package com.ll.basic1.boundedContext.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Member {
    private static int lastId;
    private final long id;
    private final String username;
    private final String password;

    static {
        lastId = 1;
    }
    public Member(String username, String password) {
        this(++lastId, username, password);
    }
}