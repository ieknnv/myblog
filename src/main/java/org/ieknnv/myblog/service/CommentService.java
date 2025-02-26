package org.ieknnv.myblog.service;

import java.util.List;

import org.ieknnv.myblog.dto.CommentDto;

public interface CommentService {

    void addCommentToPost(CommentDto commentDto);
    List<CommentDto> getComments(Integer postId);
}
