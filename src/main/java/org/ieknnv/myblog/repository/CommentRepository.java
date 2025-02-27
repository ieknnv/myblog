package org.ieknnv.myblog.repository;

import java.util.List;

import org.ieknnv.myblog.model.Comment;

public interface CommentRepository {
    List<Comment> findAllByPostId(Integer postId);
    void save(Comment comment);
    void updateContent(Integer commentId, String newContent);
}
