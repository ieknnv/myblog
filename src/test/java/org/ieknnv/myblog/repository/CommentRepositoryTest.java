package org.ieknnv.myblog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.ieknnv.myblog.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommentRepositoryTest extends AbstractDaoTest {

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        super.setUp();
        // Добавление тестовых комментариев
        jdbcTemplate.execute("INSERT INTO post_comment (post_id, nickname, content) VALUES (1, 'Nickname 1', 'First " +
                "comment for the first post')");
        jdbcTemplate.execute("INSERT INTO post_comment (post_id, nickname, content) VALUES (1, 'Nickname 2', 'Second " +
                "comment for the first post')");
        jdbcTemplate.execute("INSERT INTO post_comment (post_id, nickname, content) VALUES (2, 'Nickname 3', 'First " +
                "comment for the second post')");
    }

    @Test
    void testFindAllByPostId() {
        List<Comment> comments = commentRepository.findAllByPostId(1);
        assertEquals(2, comments.size());

        Comment firstComment = comments.getFirst();
        assertEquals(1, firstComment.getPostId());
        assertEquals("Nickname 1", firstComment.getNickName());
        assertEquals("First comment for the first post", firstComment.getContent());

        Comment secondComment = comments.getLast();
        assertEquals(1, secondComment.getPostId());
        assertEquals("Nickname 2", secondComment.getNickName());
        assertEquals("Second comment for the first post", secondComment.getContent());
    }

    @Test
    void testSave() {
        var newComment = Comment.builder()
                .postId(2)
                .nickName("Nickname 4")
                .content("Second comment for the second post")
                .build();
        commentRepository.save(newComment);
        var comments = commentRepository.findAllByPostId(2);
        assertEquals(2, comments.size());
    }
}
