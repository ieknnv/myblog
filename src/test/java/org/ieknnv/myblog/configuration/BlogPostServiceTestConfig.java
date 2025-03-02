package org.ieknnv.myblog.configuration;

import static org.mockito.Mockito.mock;

import org.ieknnv.myblog.repository.BlogPostRepository;
import org.ieknnv.myblog.service.BlogPostService;
import org.ieknnv.myblog.service.BlogPostServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogPostServiceTestConfig {

    @Bean
    public BlogPostService blogPostService(BlogPostRepository blogPostRepository) {
        return new BlogPostServiceImpl(blogPostRepository);
    }

    @Bean
    public BlogPostRepository blogPostRepository() {
        return mock(BlogPostRepository.class);
    }
}