package org.ieknnv.myblog.repository;

import java.util.List;

import org.ieknnv.myblog.model.BlogPost;

public interface BlogPostRepository {

    void save(BlogPost post);

    void update(BlogPost updatedPost);

    byte[] findImageByPostId(Integer postId);

    List<BlogPost> getPostFeed();

    BlogPost findPostById(Integer postId);

    void addLike(Integer postId);

    void delete(Integer postId);

    List<BlogPost> findByTags(List<String> tags);
}
