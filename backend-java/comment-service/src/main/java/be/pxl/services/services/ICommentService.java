package be.pxl.services.services;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;

import java.util.List;

public interface ICommentService {
    void createComment(CommentRequest commentRequest);
    List<CommentResponse> getComments();
    List<CommentResponse> getCommentsByPostId(Long postId);
}
