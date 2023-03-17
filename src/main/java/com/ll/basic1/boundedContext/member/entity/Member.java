package com.ll.basic1.boundedContext.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private  long id;
    @CreatedDate
    private LocalDateTime create_date;
    @LastModifiedDate
    private LocalDateTime modify_date;
    @Column(unique = true)
    private String username;
    private String password;
}