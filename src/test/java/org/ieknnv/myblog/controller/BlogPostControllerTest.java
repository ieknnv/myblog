package org.ieknnv.myblog.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.service.BlogPostService;
import org.ieknnv.myblog.service.BlogPostServiceImpl;
import org.ieknnv.myblog.service.CommentService;
import org.ieknnv.myblog.service.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = {BlogPostController.class, BlogPostServiceImpl.class, CommentServiceImpl.class})
@AutoConfigureMockMvc
class BlogPostControllerTest {

    @MockitoBean
    private BlogPostService blogPostService;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private BlogPostController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPostFound() throws Exception {
        var postDto = BlogPostDto.builder()
                .id(1)
                .build();
        when(blogPostService.findPostById(1)).thenReturn(Optional.of(postDto));
        when(commentService.getComments(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("postDto"))
                .andExpect(model().attributeExists("comments"));
    }

    @Test
    void testPostNotFound() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePost() throws Exception {
        doNothing().when(blogPostService).deletePost(1);
        mockMvc.perform(post("/posts/1/delete").param("_method", "delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/feed"));
    }
}
