package com.skmservice.domain.post.dto;

import com.skmservice.domain.member.entity.Member;
import com.skmservice.domain.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

public record PostCreateRequest(
        String title,
        String content,
        MultipartFile file  // 파일 추가
) {
    public static Post toEntity(Member member, PostCreateRequest request) {
        return Post.builder()
                .member(member)
                .title(request.title)
                .content(request.content)
                .fileAttached(request.file != null && !request.file.isEmpty())  // 파일이 있으면 true
                .build();
    }
}
