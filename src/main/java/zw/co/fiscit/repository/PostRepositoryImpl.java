package zw.co.fiscit.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import zw.co.fiscit.exception.JdbcException;
import zw.co.fiscit.model.Post;
import zw.co.fiscit.services.CommentService;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository

public class PostRepositoryImpl implements PostRepository {

    @Value("${MYSQL_DATABASE_NAME}")
    private String mysqlDb;
    private final JdbcTemplate mysqlJdbcTemplate;
    private final CommentService commentService;

    private final Logger LOGGER = LoggerFactory.getLogger(PostRepositoryImpl.class);

    public PostRepositoryImpl(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate, CommentService commentService) {
        this.mysqlJdbcTemplate = mysqlJdbcTemplate;
        this.commentService = commentService;
    }

    @Override
    public List<Post> findAllBlogs() {
       String sql = String.format("SELECT * FROM %s.posts",mysqlDb);
        return mysqlJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Post.class));
    }

    @Override
    public List<Post> findAllPaginatedBlogs(Integer page, Integer pageSize) {
        if(page <= 0)
            throw new JdbcException("page size has to be greater than 0.", HttpStatus.BAD_REQUEST);
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM " + mysqlDb + ".posts LIMIT " + pageSize + " OFFSET " + offset;
        return mysqlJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Post.class));
    }

//    this is for adding comments to a list of posts
    private void addCommentsToPost(List<Post> posts) {
        posts.forEach(post -> {
            post.setComments(commentService.findByPostId(post.getId()));
        });
    }

    @Override
    public Post findByBlogById(Long blogId) {
        String sql =String.format("SELECT * FROM %s.posts WHERE `id` = ?",mysqlDb);
        try {
            Post post = mysqlJdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(Post.class), blogId);
            post.setComments(commentService.findByPostId(post.getId()));
            return post;
        } catch (Exception e) {
            throw new JdbcException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Integer blogCount() {
        String sql = String.format("SELECT COUNT(*) FROM %s.posts", mysqlDb);
        return mysqlJdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Post save(Post post) {
        try{
            int count = getCount(post);
            if (count > 0) {
                throw new JdbcException("A post with the title '" + post.getTitle() + "' already exists.", HttpStatus.CONFLICT);
            }
            String sql = "INSERT INTO "+mysqlDb+".posts (`title`,`content`,`posted_by`) VALUES (?,?,?)";
            LOGGER.info("sql: "+ sql);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            mysqlJdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getContent());
                ps.setString(3, post.getPostedBy());
                return ps;
            }, keyHolder);

            post.setId(keyHolder.getKey().longValue());
            return post;

        }catch (Exception e){
            throw new JdbcException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public Integer getCount(Post post) {
        String checkSql = "SELECT COUNT(*) FROM " + mysqlDb + ".posts WHERE `title` = ?";
        return mysqlJdbcTemplate.queryForObject(checkSql, Integer.class, post.getTitle());
    }


}
