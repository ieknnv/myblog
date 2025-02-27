package org.ieknnv.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.ieknnv.myblog.dto.CommentDto;
import org.ieknnv.myblog.dto.CommentUpdateDto;
import org.ieknnv.myblog.mapper.CommentMapper;
import org.ieknnv.myblog.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addCommentToPost(CommentDto commentDto) {
        repository.save(CommentMapper.toModel(commentDto));
    }

    @Override
    public List<CommentDto> getComments(Integer postId) {
        return repository.findAllByPostId(postId).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateContent(CommentUpdateDto commentUpdateDto) {
        repository.updateContent(commentUpdateDto.getCommentId(), commentUpdateDto.getUpdatedCommentContent());
    }

    @Override
    public void delete(Integer commentId) {
        repository.delete(commentId);
    }
}
