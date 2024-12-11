package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;

    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream().map(post -> PostResponse.builder()
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getDraftPosts() {
        return postRepository.findAllByStatus(Status.valueOf("DRAFT")).stream().map(post -> PostResponse.builder()
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getApprovedPosts() {
        return postRepository.findAllByStatus(Status.valueOf("APPROVED")).stream().map(post -> PostResponse.builder()
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .build()).toList();
    }

    public PostResponse createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .reviewId(postRequest.getReviewId())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .status(postRequest.getStatus())
                .build();
        postRepository.save(post);
        return PostResponse.builder()
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .build();
    }
}
