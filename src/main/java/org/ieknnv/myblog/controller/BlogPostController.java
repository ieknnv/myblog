package org.ieknnv.myblog.controller;

import java.io.IOException;
import java.util.List;

import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.dto.CommentDto;
import org.ieknnv.myblog.dto.CommentUpdateDto;
import org.ieknnv.myblog.service.BlogPostService;
import org.ieknnv.myblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/posts")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final CommentService commentService;

    public BlogPostController(BlogPostService blogPostService, CommentService commentService) {
        this.blogPostService = blogPostService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable(name = "id") Integer id, Model model) {
        BlogPostDto blogPostDto = blogPostService.findPostById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog post not found!"));
        List<CommentDto> commentDtos = commentService.getComments(id);
        model.addAttribute("postDto", blogPostDto);
        model.addAttribute("comments", commentDtos);

        return "post";
    }

    @PostMapping
    public String addPost(@ModelAttribute BlogPostDto newPost) throws IOException {
        blogPostService.createPost(newPost);
        return "redirect:/feed";
    }

    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable(name = "postId") int postId, @ModelAttribute BlogPostDto updatedPost) throws IOException {
        updatedPost.setId(postId);
        blogPostService.updatePost(updatedPost);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable(name = "postId") int postId, @ModelAttribute CommentDto commentDto) {
        commentService.addCommentToPost(commentDto);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/comments/{commentId}")
    public String updateComment(@PathVariable(name = "postId") int postId,
            @PathVariable(name = "commentId") int commentId, @ModelAttribute CommentUpdateDto commentUpdateDto) {
        commentUpdateDto.setCommentId(commentId);
        commentService.updateContent(commentUpdateDto);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/likes")
    public String addLike(@PathVariable(name = "postId") int postId) {
        blogPostService.addLike(postId);
        return "redirect:/posts/" + postId;
    }

    @PostMapping(value = "/{postId}/comments/{commentId}/delete", params = "_method=delete")
    public String deleteComment(@PathVariable(name = "postId") int postId,
            @PathVariable(name = "commentId") int commentId) {
        commentService.delete(commentId);
        return "redirect:/posts/" + postId;
    }

    @PostMapping(value = "/{postId}/delete", params = "_method=delete")
    public String deletePost(@PathVariable(name = "postId") int postId) {
        blogPostService.deletePost(postId);
        return "redirect:/feed";
    }

    @ExceptionHandler(value = IOException.class)
    public String handleException(Model model) {
        model.addAttribute("error", "Не удалось загрузить картинку поста!");
        return "error";
    }
}
