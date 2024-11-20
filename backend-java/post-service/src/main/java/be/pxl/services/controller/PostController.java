package be.pxl.services.controller;

import be.pxl.services.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    @GetMapping
    public ResponseEntity<?> getPosts() {
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }
}
