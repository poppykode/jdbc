package zw.co.fiscit.services;

import org.springframework.stereotype.Service;
import zw.co.fiscit.model.Post;
import zw.co.fiscit.repository.PostRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Map<String, Object> findAllPaginatedPosts(Integer page, Integer pageSize){
        int totalPosts = postRepository.blogCount();
        Map<String, Object> response = new HashMap<>();

        if (page == null && pageSize == null) {
            List<Post> allPosts = postRepository.findAllBlogs();
            response.put("posts", allPosts);
            response.put("totalPosts", totalPosts);
        } else {
            List<Post> allPaginatedPosts = postRepository.findAllPaginatedBlogs(page, pageSize);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            response.put("posts", allPaginatedPosts);
            response.put("currentPage", page);
            response.put("pageSize", pageSize);
            response.put("totalPages", totalPages);
            response.put("totalPosts", totalPosts);
        }
         return response;
        }

    public Post save(Post post) {
        return postRepository.save(post);
    }
}
