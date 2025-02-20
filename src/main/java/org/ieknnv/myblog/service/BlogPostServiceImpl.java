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
import org.springframework.stereotype.Service;

@Service
public class BlogPostServiceImpl implements BlogPostService{

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public void createNewPost(BlogPostDto newPostDto) throws IOException {
        BlogPost newPost = BlogPostMapper.toModel(newPostDto);
        blogPostRepository.savePost(newPost);
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
}
