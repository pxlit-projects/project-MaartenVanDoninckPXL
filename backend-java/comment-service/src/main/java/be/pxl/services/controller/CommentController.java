package be.pxl.services.controller;

import be.pxl.services.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @GetMapping
    public ResponseEntity<?> getComments() {
        return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
    }
}
