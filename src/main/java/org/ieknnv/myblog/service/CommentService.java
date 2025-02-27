package org.ieknnv.myblog.service;

import java.util.List;

import org.ieknnv.myblog.dto.CommentDto;
import org.ieknnv.myblog.dto.CommentUpdateDto;

public interface CommentService {

    void addCommentToPost(CommentDto commentDto);
    List<CommentDto> getComments(Integer postId);
    void updateContent(CommentUpdateDto commentUpdateDto);
    void delete(Integer commentId);
}
