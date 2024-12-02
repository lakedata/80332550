package com.skmservice.domain.post.dto;

import com.skmservice.domain.post.entity.Post;

public record PostReadResponse(
        Long id,
        String title,
        String content,
        String member,
        String createdAt
) {
    public static PostReadResponse fromEntity(Post post) {
        return new PostReadResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getEmail(),
                post.getCreatedAt().toString()
        );
    }
}