package org.ieknnv.myblog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.ieknnv.myblog.dto.BlogPostPreviewDto;
import org.ieknnv.myblog.dto.TagSearchRequestDto;
import org.ieknnv.myblog.mapper.BlogPostMapper;
import org.ieknnv.myblog.model.BlogPost;
import org.ieknnv.myblog.service.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String getFeed(Model model) {
        List<BlogPostPreviewDto> feed = blogPostService.getPostFeed();
        model.addAttribute("feed", feed);
        return "feed";
    }

    @GetMapping("/pages/{pageNumber}/{pageSize}")
    public String getPage(@PathVariable(name = "pageNumber") int pageNumber,
            @PathVariable(name = "pageSize") int pageSize, Model model) {
        Page<BlogPost> page = blogPostService.getPostFeedPage(pageNumber - 1, pageSize);
        List<BlogPostPreviewDto> feed = page.stream()
                .map(BlogPostMapper::toPreviewDto)
                .collect(Collectors.toList());

        model.addAttribute("numberOfPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("feed", feed);
        return "feed";
    }

    @PostMapping("/filter-by-tags")
    public String filterByTags(@ModelAttribute TagSearchRequestDto tagSearchRequestDto, Model model) {
        String tagsString = tagSearchRequestDto.getTagsSeparatedByComma();
        if (StringUtils.isEmpty(tagsString)) {
            return "redirect:/feed";
        }
        List<BlogPostPreviewDto> feedFilteredByTags =
                blogPostService.filterFeedByTags(BlogPostMapper.getTags(tagsString));
        model.addAttribute("feed", feedFilteredByTags);
        return "feed";
    }
}
