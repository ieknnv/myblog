package org.ieknnv.myblog.controller;

import java.io.IOException;

import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.service.BlogPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feed")
public class FeedController {

    private final BlogPostService blogPostService;

    public FeedController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public String getFeed() {
        return "feed";
    }

    @PostMapping
    public String addPost(@ModelAttribute BlogPostDto newPost) throws IOException {
        blogPostService.createNewPost(newPost);
        return "redirect:/feed";
    }

    @ExceptionHandler(value = IOException.class)
    public String handleException(Model model) {
        model.addAttribute("error", "Не удалось загрузить картинку поста!");
        return "error";
    }
}
