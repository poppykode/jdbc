package zw.co.fiscit.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import zw.co.fiscit.exception.JdbcException;
import zw.co.fiscit.model.Comment;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Value("${POSTGRES_DATABASE_NAME}")
    private String postgresDb;

    private final JdbcTemplate postgresJdbcTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(CommentRepositoryImpl.class);

    public CommentRepositoryImpl(@Qualifier("postgresJdbcTemplate") JdbcTemplate postgresJdbcTemplate) {
        this.postgresJdbcTemplate = postgresJdbcTemplate;
    }

    @Override
    public Comment save(Comment comment) {
        try{
            String sql = "INSERT INTO "+postgresDb+".comments (post_id, content) VALUES (?, ?) RETURNING id";
            Long generateId = postgresJdbcTemplate.queryForObject(sql, Long.class, comment.getPostId(), comment.getContent());
            comment.setId(generateId);
        }catch (Exception e){
            LOGGER.info("error: "+e.getMessage());
        }
        return comment;
    }

    @Override
    public void deleteCommentById(Long id) {
        try{
            String sql = "DELETE FROM "+postgresDb+".comments WHERE 'id' = ?";
            postgresJdbcTemplate.update(sql, id);
        }catch (Exception e){
            LOGGER.info("error: "+e.getMessage());
        }
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        try{
        String sql = "SELECT * FROM "+postgresDb+".comments WHERE `post_id` = ?";
        return postgresJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Comment.class),postId);
        }catch (Exception e){
            LOGGER.info("error: "+e.getMessage());
        }
        return null;
    }
}
