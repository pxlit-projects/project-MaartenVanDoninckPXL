package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    public List<CommentResponse> getComments() {
        return commentRepository.findAll().stream().map(comment -> CommentResponse.builder()
                .postId(comment.getPostId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build()).toList();
    }

    public void createComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .postId(commentRequest.getPostId())
                .author(commentRequest.getAuthor())
                .content(commentRequest.getContent())
                .build();
        commentRepository.save(comment);
    }
}
