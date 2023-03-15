package com.ll.basic1.boundedContext.member.repository;

import com.ll.basic1.boundedContext.member.entity.Member;
import lombok.Getter;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    private final List<Member> memberList;
    public MemberRepository() {
        memberList = new ArrayList<>() {{
            add(new Member("user1", "1234"));
            add(new Member("abc", "12345"));
            add(new Member("test", "12346"));
            add(new Member("love", "12347"));
            add(new Member("like", "12348"));
            add(new Member("giving", "12349"));
            add(new Member("thanks", "123410"));
            add(new Member("hello", "123411"));
            add(new Member("good", "123412"));
            add(new Member("peace", "123413"));
        }};
    }

    public Member findByUsername(String username) {
        return memberList
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}


