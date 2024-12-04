package com.skmservice.domain.post.dto;

import com.skmservice.domain.post.entity.Post;

public record PostReadResponse(
        Long id,
        String title,
        String content,
        int viewCount,
        String member,
        String createdAt
) {
    public static PostReadResponse fromEntity(Post post) {
        return new PostReadResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getMember().getEmail(),
                post.getCreatedAt().toString()
        );
    }
}
