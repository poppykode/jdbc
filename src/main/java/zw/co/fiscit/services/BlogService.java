package zw.co.fiscit.services;

import org.springframework.stereotype.Service;
import zw.co.fiscit.model.Blog;
import zw.co.fiscit.repository.BlogRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Map<String, Object> findAllPaginatedPosts(Integer page, Integer pageSize){
        int totalPosts = blogRepository.blogCount();
        Map<String, Object> response = new HashMap<>();

        if (page == null && pageSize == null) {
            List<Blog> allPosts = blogRepository.findAllBlogs();
            response.put("posts", allPosts);
            response.put("totalPosts", totalPosts);
        } else {
            List<Blog> allPaginatedPosts = blogRepository.findAllPaginatedBlogs(page, pageSize);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            response.put("posts", allPaginatedPosts);
            response.put("currentPage", page);
            response.put("pageSize", pageSize);
            response.put("totalPages", totalPages);
            response.put("totalPosts", totalPosts);
        }
         return response;
        }
}
