package be.pxl.services.controller;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse createdPost = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts() {
        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }

    @GetMapping("/draft")
    public ResponseEntity<List<PostResponse>> getDraftPosts() {
        return new ResponseEntity<>(postService.getDraftPosts(), HttpStatus.OK);
    }

    @GetMapping("/draft/{author}")
    public ResponseEntity<List<PostResponse>> getDraftPostsByAuthor(@PathVariable String author) {
        return new ResponseEntity<>(postService.getDraftPostsByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<PostResponse>> getApprovedPosts() {
        return new ResponseEntity<>(postService.getApprovedPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        PostResponse updatedPost = postService.updatePost(id, postRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }
}
