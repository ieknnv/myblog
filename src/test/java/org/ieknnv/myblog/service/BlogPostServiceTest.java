package org.ieknnv.myblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.ieknnv.myblog.configuration.BlogPostServiceTestConfig;
import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.model.BlogPost;
import org.ieknnv.myblog.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = BlogPostServiceTestConfig.class)
class BlogPostServiceTest {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogPostServiceImpl blogPostService;

    @BeforeEach
    void setUp() {
        Mockito.reset(blogPostRepository);
    }

    @Test
    void testFindPostById() {
        BlogPost postMock = BlogPost.builder()
                .id(1)
                .name("Mock post")
                .text("Mock text")
                .numberOfLikes(10)
                .tags(Arrays.asList("#tag1", "#tag2"))
                .build();
        Mockito.when(blogPostRepository.findPostById(1)).thenReturn(postMock);

        Optional<BlogPostDto> postOpt = blogPostService.findPostById(1);
        assertTrue(postOpt.isPresent(), "Пост должен быть найден");
        BlogPostDto postDto = postOpt.get();
        assertEquals(1, postDto.getId());
        assertEquals("Mock post", postDto.getName());
        assertEquals("Mock text", postDto.getText());
        assertEquals(10, postDto.getNumberOfLikes());
        assertEquals("#tag1, #tag2", postDto.getTagsSeparatedByComma());
    }

    @Test
    void testCreatePost() throws IOException {
        var newPostDto = BlogPostDto.builder()
                .name("New post title")
                .text("New post text")
                .numberOfLikes(10)
                .tagsSeparatedByComma("#tag1, #tag2")
                .build();
        blogPostService.createPost(newPostDto);

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(captor.capture());

        var blogPost = captor.getValue();
        assertNotNull(blogPost);
        assertEquals("New post title", blogPost.getName());
        assertEquals("New post text", blogPost.getText());
        assertEquals(10, blogPost.getNumberOfLikes());
        var tags = blogPost.getTags();
        assertEquals(2, tags.size());
        assertTrue(tags.contains("#tag1"));
        assertTrue(tags.contains("#tag2"));
    }
}
