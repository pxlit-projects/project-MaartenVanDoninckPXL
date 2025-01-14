package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;

    public void createComment(CommentRequest commentRequest) {
        logger.info("Creating comment for post with id {}", commentRequest.getPostId());
        Comment comment = Comment.builder()
                .postId(commentRequest.getPostId())
                .author(commentRequest.getAuthor())
                .content(commentRequest.getContent())
                .build();
        commentRepository.save(comment);
        logger.info("Comment created for post with id {}", commentRequest.getPostId());
    }

    public List<CommentResponse> getComments() {
        logger.info("Getting all comments");
        return commentRepository.findAll().stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build()).toList();
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        logger.info("Getting all comments for post with id {}", postId);
        return commentRepository.findAllByPostId(postId).stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build()).toList();
    }

    @Override
    public void updateComment(Long id, CommentRequest commentRequest) {
        logger.info("Updating comment with id {}", id);
        Comment comment = commentRepository.findById(id).orElseThrow();
        if (!Objects.equals(comment.getAuthor(), commentRequest.getAuthor())) {
            throw new IllegalArgumentException("Only the author can update the comment");
        }
        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
        logger.info("Comment updated with id {}", id);
    }

    @Override
    public void deleteComment(Long id) {
        logger.info("Deleting comment with id {}", id);
        commentRepository.deleteById(id);
        logger.info("Comment deleted with id {}", id);
    }
}
