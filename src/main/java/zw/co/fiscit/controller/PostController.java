package zw.co.fiscit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.fiscit.model.Post;
import zw.co.fiscit.services.PostService;

import java.util.Map;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> posts(@RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(postService.findAllPaginatedPosts(page,pageSize));
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }


}
