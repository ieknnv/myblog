package org.ieknnv.myblog.repository;

import java.util.List;

import org.ieknnv.myblog.model.BlogPost;

public interface BlogPostRepository {

    void savePost(BlogPost post);

    byte[] findImageByPostId(Integer postId);

    List<BlogPost> getPostFeed();

    BlogPost findPostById(Integer postId);

    void addLike(Integer postId);
}
