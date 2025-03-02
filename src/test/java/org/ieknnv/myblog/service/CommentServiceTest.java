package org.ieknnv.myblog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.ieknnv.myblog.configuration.CommentServiceTestConfig;
import org.ieknnv.myblog.dto.CommentDto;
import org.ieknnv.myblog.model.Comment;
import org.ieknnv.myblog.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = CommentServiceTestConfig.class)
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        Mockito.reset(commentRepository);
    }

    @Test
    void testAddCommentToPost() {
        var newCommentDto = CommentDto.builder()
                .postId(1)
                .nickName("Nickname 1")
                .content("Comment 1")
                .build();
        commentService.addCommentToPost(newCommentDto);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());

        var comment = captor.getValue();
        assertNotNull(comment);
        assertEquals("Nickname 1", comment.getNickName());
        assertEquals("Comment 1", comment.getContent());
        assertEquals(1, comment.getPostId());
    }

    @Test
    void testGetComments() {
        var comment1 = Comment.builder()
                .id(1)
                .postId(1)
                .nickName("Nickname 1")
                .content("Comment 1")
                .build();
        var comment2 = Comment.builder()
                .id(2)
                .postId(1)
                .nickName("Nickname 2")
                .content("Comment 2")
                .build();
        var comments = Arrays.asList(comment1, comment2);
        Mockito.when(commentRepository.findAllByPostId(1)).thenReturn(comments);

        var commentDtos = commentService.getComments(1);
        assertEquals(2, commentDtos.size());
        var firstCommentDto = commentDtos.getFirst();
        assertEquals(1, firstCommentDto.getId());
        assertEquals(1, firstCommentDto.getPostId());
        assertEquals("Nickname 1", firstCommentDto.getNickName());
        assertEquals("Comment 1", firstCommentDto.getContent());
    }
}
