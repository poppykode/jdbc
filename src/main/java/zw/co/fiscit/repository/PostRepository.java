package zw.co.fiscit.repository;

import zw.co.fiscit.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAllBlogs();
    List<Post> findAllPaginatedBlogs(Integer page, Integer pageSize);
    Post findByBlogById(Long blogId);
    Integer blogCount();
    Post save(Post post);
    Integer getCount(Post post);

}
