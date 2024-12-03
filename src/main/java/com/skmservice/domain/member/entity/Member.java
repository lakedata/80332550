package com.skmservice.domain.member.entity;

import com.skmservice.domain.member.dto.request.MemberCreateRequest;
import com.skmservice.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "member")
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", unique = true)
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_name", unique = true)
    private String name;

    @Builder
    public Member(MemberCreateRequest request) {
        this.email = request.email();
        this.password = request.password();
        this.name = request.name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
