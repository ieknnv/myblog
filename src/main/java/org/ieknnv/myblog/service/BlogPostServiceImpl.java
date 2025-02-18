package org.ieknnv.myblog.service;

import java.io.IOException;

import org.ieknnv.myblog.dto.BlogPostDto;
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
}
