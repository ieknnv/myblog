package org.ieknnv.myblog.repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.ieknnv.myblog.model.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl implements CommentRepository{

    private static final String SELECT_COMMENTS_BY_POST_QUERY = """
            SELECT * FROM post_comment
            WHERE post_id = :postId
            ORDER BY id
            """;
    private static final String UPDATE_COMMENT_CONTENT_QUERY = """
            UPDATE post_comment
            SET content = :newContent
            WHERE id = :commentId
            """;
    private static final String DELETE_COMMENT_QUERY = """
            DELETE FROM post_comment
            WHERE id = :commentId
            """;
    private static final String POST_ID_COLUMN = "post_id";
    private static final String NICKNAME_COLUMN = "nickname";
    private static final String CONTENT_COLUMN = "content";
    public static final String ID_COLUMN = "id";
    public static final String POST_COMMENT_TABLE = "post_comment";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertComment;

    // RowMappers
    private final RowMapper<Comment> commentRowMapper = (ResultSet resultSet, int rowNum) -> Comment.builder()
            .id(resultSet.getInt(ID_COLUMN))
            .postId(resultSet.getInt(POST_ID_COLUMN))
            .nickName(resultSet.getString(NICKNAME_COLUMN))
            .content(resultSet.getString(CONTENT_COLUMN))
            .build();

    public CommentRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertComment = new SimpleJdbcInsert(dataSource).withTableName(POST_COMMENT_TABLE)
                .usingColumns(POST_ID_COLUMN, NICKNAME_COLUMN, CONTENT_COLUMN);
    }

    @Override
    public List<Comment> findAllByPostId(Integer postId) {
        SqlParameterSource parameters = new MapSqlParameterSource("postId", postId);
        return namedParameterJdbcTemplate.query(SELECT_COMMENTS_BY_POST_QUERY, parameters, commentRowMapper);
    }

    @Override
    public void save(Comment comment) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(POST_ID_COLUMN, comment.getPostId());
        parameters.put(NICKNAME_COLUMN, comment.getNickName());
        parameters.put(CONTENT_COLUMN, comment.getContent());
        insertComment.execute(parameters);
    }

    @Override
    public void updateContent(Integer commentId, String newContent) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("commentId", commentId);
        parameters.put("newContent", newContent);
        namedParameterJdbcTemplate.update(UPDATE_COMMENT_CONTENT_QUERY, parameters);
    }

    @Override
    public void delete(Integer commentId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("commentId", commentId);
        namedParameterJdbcTemplate.update(DELETE_COMMENT_QUERY, parameters);
    }
}
