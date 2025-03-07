package org.ieknnv.myblog.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.ieknnv.myblog.dto.BlogPostDto;
import org.ieknnv.myblog.dto.BlogPostPreviewDto;
import org.ieknnv.myblog.mapper.BlogPostMapper;
import org.ieknnv.myblog.model.BlogPost;
import org.ieknnv.myblog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BlogPostServiceImpl implements BlogPostService{

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public void createPost(BlogPostDto newPostDto) throws IOException {
        BlogPost newPost = BlogPostMapper.toModel(newPostDto);
        blogPostRepository.save(newPost);
    }

    @Override
    public void updatePost(BlogPostDto updatedPostDto) throws IOException {
        BlogPost updatedPost = BlogPostMapper.toModel(updatedPostDto);
        blogPostRepository.update(updatedPost);
    }

    @Override
    public Optional<byte[]> findImageByPostId(Integer postId) {
        byte[] imageBytes = blogPostRepository.findImageByPostId(postId);
        return ArrayUtils.isNotEmpty(imageBytes) ? Optional.of(imageBytes) : Optional.empty();
    }

    @Override
    public List<BlogPostPreviewDto> getPostFeed() {
        return blogPostRepository.getPostFeed()
                .stream()
                .map(BlogPostMapper::toPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BlogPostDto> findPostById(Integer postId) {
        return Optional.ofNullable(blogPostRepository.findPostById(postId))
                .map(BlogPostMapper::toDto);
    }

    @Override
    public void addLike(Integer postId) {
        blogPostRepository.addLike(postId);
    }

    @Override
    public void deletePost(Integer postId) {
        blogPostRepository.delete(postId);
    }

    @Override
    public List<BlogPostPreviewDto> filterFeedByTags(List<String> tags) {
        return blogPostRepository.findByTags(tags)
                .stream()
                .map(BlogPostMapper::toPreviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BlogPost> getPostFeedPage(int pageNumber, int pageSize) {
        return blogPostRepository.getPostFeedPage(PageRequest.of(pageNumber, pageSize));
    }
}
