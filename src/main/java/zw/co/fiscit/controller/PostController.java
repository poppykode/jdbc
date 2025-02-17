package zw.co.fiscit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zw.co.fiscit.services.PostService;

import java.util.Map;

@RestController
@RequestMapping("api/v1/")
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


}
