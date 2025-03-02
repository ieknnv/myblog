package org.ieknnv.myblog.repository;

import org.ieknnv.myblog.configuration.DataSourceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {DataSourceTestConfig.class, BlogPostRepositoryImpl.class, CommentRepositoryImpl.class})
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class AbstractDaoTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Очищаем БД
        jdbcTemplate.execute("DELETE FROM post_tag_relation");
        jdbcTemplate.execute("DELETE FROM post_tag");
        jdbcTemplate.execute("DELETE FROM post_comment");
        jdbcTemplate.execute("DELETE FROM blog_post");

        jdbcTemplate.execute("ALTER TABLE blog_post ALTER COLUMN id RESTART WITH 1");

        // Заполняем БД тестовыми данными
        jdbcTemplate.execute("INSERT INTO blog_post (post_name, post_text, number_of_likes) VALUES ('Test post 1 " +
                "title', 'Test post 1 text', 5)");
        jdbcTemplate.execute("INSERT INTO blog_post (post_name, post_text, number_of_likes) VALUES ('Test post 2 " +
                "title', 'Test post 2 text', 10)");
        jdbcTemplate.execute("INSERT INTO blog_post (post_name, post_text, number_of_likes) VALUES ('Test post 3 " +
                "title', 'Test post 3 text', 15)");
    }
}
