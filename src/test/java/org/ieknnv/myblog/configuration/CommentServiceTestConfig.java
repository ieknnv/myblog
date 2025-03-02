package org.ieknnv.myblog.configuration;

import static org.mockito.Mockito.mock;

import org.ieknnv.myblog.repository.CommentRepository;
import org.ieknnv.myblog.service.CommentService;
import org.ieknnv.myblog.service.CommentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentServiceTestConfig {

    @Bean
    public CommentService commentService(CommentRepository commentRepository) {
        return new CommentServiceImpl(commentRepository);
    }

    @Bean
    public CommentRepository commentRepository() {
        return mock(CommentRepository.class);
    }
}
