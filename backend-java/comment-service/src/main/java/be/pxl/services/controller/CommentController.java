package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments() {
        return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(id, commentRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
