package org.ieknnv.myblog.controller;

import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.service.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/post")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable(name = "id") Integer id, Model model) {
        BlogPostDto blogPostDto = blogPostService.findPostById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog post not found!"));
        model.addAttribute("postDto", blogPostDto);

        return "post";
    }
}
