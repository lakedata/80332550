package com.skmservice.domain.post.controller;

import com.skmservice.domain.post.entity.Post;
import com.skmservice.domain.post.repository.PostJpaRepository;
import com.skmservice.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostMappingController {
    private final PostService postService;
    private final PostJpaRepository postRepository;

    @GetMapping("/list")
    public String showRegisterPage() {
        return "postList";
    }

    @GetMapping("/add")
    public String RegisterPage() {
        return "postAdd";
    }

    @GetMapping("/{postId}/detail")
    public String getPostDetail(@PathVariable Long postId, Model model) {
        System.out.println("Post ID: " + postId);

        Post post = postService.getPostById(postId);
        post.increaseViewCount();
        postRepository.save(post);
        model.addAttribute("post", post);
        return "postDetail";
    }

    @GetMapping("/{postId}/update")
    public String getPostUpdate(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);
        return "postUpdate";  // 수정 페이지로 이동
    }

}
