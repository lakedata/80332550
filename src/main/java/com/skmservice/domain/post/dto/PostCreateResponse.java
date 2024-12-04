package com.skmservice.domain.post.dto;

import com.skmservice.domain.post.entity.Post;

public record PostCreateResponse(
        Long id,
        String title,
        String content,
        String filePath

) {

    public static PostCreateResponse fromEntity(Post post) {
        return new PostCreateResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getFilePath()
        );
    }
}
