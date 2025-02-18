package zw.co.fiscit.repository;

import zw.co.fiscit.model.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    void deleteCommentById(Long id);

    List<Comment> findByPostId(Long postId);

}
