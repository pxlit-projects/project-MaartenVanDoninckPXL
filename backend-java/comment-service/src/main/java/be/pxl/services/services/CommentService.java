package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    public void createComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .postId(commentRequest.getPostId())
                .author(commentRequest.getAuthor())
                .content(commentRequest.getContent())
                .build();
        commentRepository.save(comment);
    }

    public List<CommentResponse> getComments() {
        return commentRepository.findAll().stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build()).toList();
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId).stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build()).toList();
    }

    @Override
    public void updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        if (!Objects.equals(comment.getAuthor(), commentRequest.getAuthor())) {
            throw new IllegalArgumentException("Only the author can update the comment");
        }
        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
