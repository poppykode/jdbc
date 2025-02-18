package zw.co.fiscit.services;

import org.springframework.stereotype.Service;
import zw.co.fiscit.model.Comment;
import zw.co.fiscit.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment) {
      return  commentRepository.save(comment);
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
