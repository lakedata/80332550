package com.skmservice.domain.post.entity;

import com.skmservice.domain.member.entity.Member;
import com.skmservice.domain.post.dto.PostCreateRequest;
import com.skmservice.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @NotNull
    @Size(min = 1, max = 255)
    private String title;


    @Column(name = "content", nullable = false)
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 게시글 작성자

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "file_attached")
    private boolean fileAttached = false;

    @Column(name = "file_path")  // 파일 경로를 저장할 컬럼
    private String filePath;

    @Builder
    public Post(PostCreateRequest request, Member member, String filePath) {
        this.title = request.title();
        this.content = request.content();
        this.member = member;
        this.filePath = filePath;  // 파일 경로 저장
        this.fileAttached = request.fileAttached(); // 첨부 파일 여부 설정
    }

    public static Post toEntity(Member member, PostCreateRequest request) {
        return Post.builder()
                .member(member)
                .title(request.title())
                .content(request.content())
                .fileAttached(request.fileAttached())
                .filePath(request.filePath())
                .build();
    }
    public void increaseViewCount() {
        this.viewCount++;
    }

    public boolean isOwnedBy(Member member) {
        return this.member.equals(member);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public boolean getFileAttached() {
        return fileAttached;
    }

}
