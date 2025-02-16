package zw.co.fiscit.repository;

import zw.co.fiscit.model.Blog;

import java.util.List;

public interface BlogRepository {
    List<Blog> findAllBlogs();
    List<Blog> findAllPaginatedBlogs(Integer page, Integer pageSize);
    Blog findByBlogById(String customerCode);
    Integer blogCount();


}
