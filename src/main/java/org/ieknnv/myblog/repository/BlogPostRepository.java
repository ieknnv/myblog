package org.ieknnv.myblog.repository;

import java.util.List;

import org.ieknnv.myblog.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostRepository {

    void save(BlogPost post);

    void update(BlogPost updatedPost);

    byte[] findImageByPostId(Integer postId);

    List<BlogPost> getPostFeed();

    BlogPost findPostById(Integer postId);

    void addLike(Integer postId);

    void delete(Integer postId);

    List<BlogPost> findByTags(List<String> tags);

    Page<BlogPost> getPostFeedPage(Pageable pageable);
}
