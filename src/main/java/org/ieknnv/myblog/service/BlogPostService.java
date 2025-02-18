package org.ieknnv.myblog.service;

import java.io.IOException;

import org.ieknnv.myblog.dto.BlogPostDto;

public interface BlogPostService {

    void createNewPost(BlogPostDto newPost) throws IOException;
}
