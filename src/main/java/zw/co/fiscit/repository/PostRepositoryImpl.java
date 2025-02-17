package zw.co.fiscit.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import zw.co.fiscit.model.Post;

import java.util.List;

@Repository

public class PostRepositoryImpl implements PostRepository {
    @Value("${POSTGRES_DATABASE_NAME}")
    private String postgresDb;
    @Value("${MYSQL_DATABASE_NAME}")
    private String mysqlDb;
    private final JdbcTemplate mysqlJdbcTemplate;

    public PostRepositoryImpl(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate) {
        this.mysqlJdbcTemplate = mysqlJdbcTemplate;
    }

    @Override
    public List<Post> findAllBlogs() {
       String sql = String.format("SELECT * FROM %s.posts",mysqlDb);
       return mysqlJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Post.class));
    }

    @Override
    public List<Post> findAllPaginatedBlogs(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = String.format("SELECT * from %s.posts LIMIT ? OFFSET ?", mysqlDb);
        return mysqlJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Post.class),pageSize,offset);
    }

    @Override
    public Post findByBlogById(Long blogId) {
        String sql =String.format("SELECT * FROM %s.posts WHERE \"id\" = ?",mysqlDb);
        try {
            return mysqlJdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(Post.class),blogId);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }

    @Override
    public Integer blogCount() {
        String sql = String.format("SELECT COUNT(*) FROM %s.posts", mysqlDb);
        return mysqlJdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Post save(Post post) {
        String sql = String.format("INSERT INTO %s.posts (\"title\", \"content\") VALUES (?,?)", mysqlDb);
        int id = mysqlJdbcTemplate.update(sql, post.getTitle(), post.getContent());
        post.setId((long) id);
        return post;
    }
}
