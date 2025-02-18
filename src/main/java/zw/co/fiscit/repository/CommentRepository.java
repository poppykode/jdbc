package zw.co.fiscit.repository;

import zw.co.fiscit.model.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
    void deleteCommentById(Long id);

}
