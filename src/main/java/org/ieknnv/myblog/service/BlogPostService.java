package org.ieknnv.myblog.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.dto.BlogPostPreviewDto;
import org.ieknnv.myblog.model.BlogPost;
import org.springframework.data.domain.Page;

public interface BlogPostService {

    void createPost(BlogPostDto newPost) throws IOException;

    void updatePost(BlogPostDto updatedPost) throws IOException;

    Optional<byte[]> findImageByPostId(Integer postId);

    List<BlogPostPreviewDto> getPostFeed();

    Optional<BlogPostDto> findPostById(Integer postId);

    void addLike(Integer postId);

    void deletePost(Integer postId);

    List<BlogPostPreviewDto> filterFeedByTags(List<String> tags);

    Page<BlogPost> getPostFeedPage(int pageNumber, int pageSize);
}
