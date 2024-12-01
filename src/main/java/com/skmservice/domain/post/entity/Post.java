package com.skmservice.domain.post.entity;

import com.skmservice.domain.member.entity.Member;
import com.skmservice.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author; // Member 엔티티와의 다대일 관계

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "file_attached")
    private boolean fileAttached = false;
}
