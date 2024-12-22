package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.DeleteReviewResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;

    public PostResponse createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .reviewId(postRequest.getReviewId())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .status(postRequest.getStatus())
                .category(postRequest.getCategory())
                .createdOn(LocalDateTime.now())
                .build();
        postRepository.save(post);
        return PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build();
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getDraftPosts() {
        return postRepository.findAllByStatus(Status.valueOf("DRAFT")).stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getApprovedPosts() {
        return postRepository.findAllByStatus(Status.valueOf("APPROVED")).stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getDraftPostsByAuthor(String author) {
        return postRepository.findAllByStatusAndAuthor(Status.valueOf("DRAFT"), author).stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getPendingPosts() {
        return postRepository.findAllByStatusIn(List.of(Status.valueOf("PENDING"), Status.valueOf("REJECTED"), Status.valueOf("APPROVED"))).stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getPendingPostsByAuthor(String author) {
        return postRepository.findAllByStatusInAndAuthor(List.of(Status.valueOf("PENDING"), Status.valueOf("REJECTED"), Status.valueOf("APPROVED")), author).stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public List<PostResponse> getPostedPosts() {
        return postRepository.findAllByStatus(Status.valueOf("POSTED")).stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build()).toList();
    }

    @Override
    public int getAmountOfReviewedPostsByAuthor(String author) {
        return postRepository.findAllByStatusInAndAuthor(List.of(Status.valueOf("APPROVED"), Status.valueOf("REJECTED")), author).size();
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        return PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build();
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setReviewId(postRequest.getReviewId());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());
        post.setStatus(postRequest.getStatus());
        post.setCategory(postRequest.getCategory());
        postRepository.save(post);
        return PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build();
    }

    @Override
    public void updatePostWithReview(ReviewRequest reviewRequest) {
        Post post = postRepository.findById(reviewRequest.getPostId()).orElseThrow();
        post.setReviewId(reviewRequest.getReviewId());
        if (reviewRequest.isApproval()) {
            post.setStatus(Status.valueOf("APPROVED"));
        } else {
            post.setStatus(Status.valueOf("REJECTED"));
        }
        postRepository.save(post);
    }

    @Override
    public void updatePostWithDeletedReview(DeleteReviewResponse deleteReviewResponse) {
        Post post = postRepository.findById(deleteReviewResponse.getPostId()).orElseThrow();
        post.setReviewId(null);
        post.setStatus(Status.valueOf("DRAFT"));
        postRepository.save(post);
    }

    @Override
    public PostResponse submitPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setStatus(Status.valueOf("POSTED"));
        postRepository.save(post);
        return PostResponse.builder()
                .id(post.getId())
                .reviewId(post.getReviewId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .category(post.getCategory())
                .createdOn(post.getCreatedOn())
                .build();
    }
}
