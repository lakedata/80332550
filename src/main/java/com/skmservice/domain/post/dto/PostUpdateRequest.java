package com.skmservice.domain.post.dto;

public record PostUpdateRequest(
        Long id,
        String title,
        String content
) {

    public static PostUpdateRequest of(Long id, String title, String content) {
        return new PostUpdateRequest(id, title, content);
    }
}
