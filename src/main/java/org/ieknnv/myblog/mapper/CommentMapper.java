package org.ieknnv.myblog.mapper;

import org.ieknnv.myblog.dto.CommentDto;
import org.ieknnv.myblog.model.Comment;

public final class CommentMapper {

    private CommentMapper() {
    }

    public static CommentDto toDto(Comment model) {
        return CommentDto.builder()
                .id(model.getId())
                .postId(model.getPostId())
                .nickName(model.getNickName())
                .content(model.getContent())
                .build();
    }

    public static Comment toModel(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .postId(dto.getPostId())
                .nickName(dto.getNickName())
                .content(dto.getContent())
                .build();
    }
}