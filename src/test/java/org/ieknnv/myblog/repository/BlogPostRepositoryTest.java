package org.ieknnv.myblog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.ieknnv.myblog.model.BlogPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class BlogPostRepositoryTest extends AbstractDaoTest {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    void testGetPostFeed() {
        List<BlogPost> posts = blogPostRepository.getPostFeed();
        assertEquals(3, posts.size());

        BlogPost post1 = posts.getFirst();
        assertEquals("Test post 1 title", post1.getName());
        assertEquals("Test post 1 text", post1.getText());
        assertEquals(5, post1.getNumberOfLikes());

        BlogPost post2 = posts.get(1);
        assertEquals("Test post 2 title", post2.getName());
        assertEquals("Test post 2 text", post2.getText());
        assertEquals(10, post2.getNumberOfLikes());

        BlogPost post3 = posts.get(2);
        assertEquals("Test post 3 title", post3.getName());
        assertEquals("Test post 3 text", post3.getText());
        assertEquals(15, post3.getNumberOfLikes());
    }

    @Test
    void testGetPostFeedPage() {
        Page<BlogPost> page = blogPostRepository.getPostFeedPage(PageRequest.of(1, 2));
        assertNotNull(page);
        assertEquals(2, page.getTotalPages());
        assertEquals(1, page.getNumberOfElements());
    }
}
