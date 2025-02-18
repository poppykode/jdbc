package zw.co.fiscit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.fiscit.model.Comment;
import zw.co.fiscit.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>>  findByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.findByPostId(postId));
    }
}
