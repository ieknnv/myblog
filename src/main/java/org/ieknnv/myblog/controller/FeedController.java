package org.ieknnv.myblog.controller;

import java.util.List;

import org.ieknnv.myblog.dto.BlogPostPreviewDto;
import org.ieknnv.myblog.service.BlogPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feed")
public class FeedController {

    private final BlogPostService blogPostService;

    public FeedController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public String getFeed(Model model) {
        List<BlogPostPreviewDto> feed = blogPostService.getPostFeed();
        model.addAttribute("feed", feed);
        return "feed";
    }
}
