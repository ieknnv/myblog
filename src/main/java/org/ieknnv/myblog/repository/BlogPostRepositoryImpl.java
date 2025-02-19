package org.ieknnv.myblog.repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;

import org.ieknnv.myblog.model.BlogPost;
import org.ieknnv.myblog.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class BlogPostRepositoryImpl implements BlogPostRepository {

    private static final String SELECT_EXISTING_TAGS_FOR_POST_QUERY = """
            SELECT * FROM post_tag
            WHERE tag_name IN (:tags)
            """;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertBlogPost;
    private final SimpleJdbcInsert insertTag;
    private final SimpleJdbcInsert insertPostTagRelation;
    private final RowMapper<Tag> tagRowMapper = (ResultSet resultSet, int rowNum) -> new Tag(
            resultSet.getInt("id"), resultSet.getString("tag_name"));

    public BlogPostRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertBlogPost = new SimpleJdbcInsert(dataSource).withTableName("blog_post")
                .usingGeneratedKeyColumns("id")
                .usingColumns("post_name", "post_text", "post_image");
        this.insertTag = new SimpleJdbcInsert(dataSource).withTableName("post_tag")
                .usingGeneratedKeyColumns("id")
                .usingColumns("tag_name");
        this.insertPostTagRelation = new SimpleJdbcInsert(dataSource).withTableName("post_tag_relation")
                .usingColumns("post_id", "tag_id");
    }

    @Override
    public void savePost(BlogPost post) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("post_name", post.getName());
        parameters.put("post_text", post.getText());
        parameters.put("post_image", post.getImage());
        Number newId = insertBlogPost.executeAndReturnKey(parameters);
        saveTags(newId.intValue(), post.getTags());
    }

    private void saveTags(int newPostId, List<String> tags) {
        if (tags.isEmpty()) {
            return;
        }
        List<Tag> existingTags = getExistingTagsForPost(tags);
        Map<String, Integer> existingTagNameToIdMap = existingTags.stream()
                .collect(Collectors.toMap(Tag::getTagName, Tag::getId));
        for (String postTag : tags) {
            Integer tagId = existingTagNameToIdMap.get(postTag);
            if (tagId == null) {
                Map<String, Object> tagInsertParameters = new HashMap<>();
                tagInsertParameters.put("tag_name", postTag);
                tagId = insertTag.executeAndReturnKey(tagInsertParameters).intValue();
            }
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("post_id", newPostId);
            parameters.put("tag_id", tagId);
            insertPostTagRelation.execute(parameters);
        }

    }

    private List<Tag> getExistingTagsForPost(List<String> postTags) {
        SqlParameterSource parameters = new MapSqlParameterSource("tags", postTags);
        return namedParameterJdbcTemplate.query(SELECT_EXISTING_TAGS_FOR_POST_QUERY, parameters, tagRowMapper);
    }
}
