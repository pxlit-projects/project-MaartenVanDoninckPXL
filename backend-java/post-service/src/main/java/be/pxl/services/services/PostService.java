package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.DeleteReviewResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;

    public PostResponse createPost(PostRequest postRequest) {
        logger.info("Creating a new post with title: {}", postRequest.getTitle());
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

        logger.debug("Post created successfully with ID: {}", post.getId());
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
        logger.info("Fetching all posts");
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
        logger.info("Fetching all draft posts");
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
    public List<PostResponse> getDraftPostsByAuthor(String author) {
        logger.info("Fetching all draft posts by author: {}", author);
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
    public List<PostResponse> getApprovedPosts() {
        logger.info("Fetching all approved posts");
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
    public List<PostResponse> getPendingPosts() {
        logger.info("Fetching all pending posts");
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
        logger.info("Fetching all pending posts by author: {}", author);
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
        logger.info("Fetching all posted posts");
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
        logger.info("Fetching the amount of reviewed posts by author: {}", author);
        return postRepository.findAllByStatusInAndAuthor(List.of(Status.valueOf("APPROVED"), Status.valueOf("REJECTED")), author).size();
    }

    @Override
    public PostResponse getPostById(Long id) {
        logger.info("Fetching post with ID: {}", id);
        Post post = postRepository.findById(id).orElseThrow();
        logger.debug("Post found with ID: {}", id);
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
        logger.info("Updating post with ID: {}", id);
        Post post = postRepository.findById(id).orElseThrow();
        post.setReviewId(postRequest.getReviewId());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());
        post.setStatus(postRequest.getStatus());
        post.setCategory(postRequest.getCategory());
        postRepository.save(post);

        logger.debug("Post updated successfully with ID: {}", id);
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
        logger.info("Updating post with review ID: {}", reviewRequest.getReviewId());
        Post post = postRepository.findById(reviewRequest.getPostId()).orElseThrow();
        post.setReviewId(reviewRequest.getReviewId());
        if (reviewRequest.isApproval()) {
            post.setStatus(Status.valueOf("APPROVED"));
        } else {
            post.setStatus(Status.valueOf("REJECTED"));
        }
        postRepository.save(post);
        logger.debug("Post updated successfully with review ID: {}", reviewRequest.getReviewId());
    }

    @Override
    public void updatePostWithDeletedReview(DeleteReviewResponse deleteReviewResponse) {
        logger.info("Updating review for post with post ID: {}", deleteReviewResponse.getPostId());
        Post post = postRepository.findById(deleteReviewResponse.getPostId()).orElseThrow();
        post.setReviewId(null);
        post.setStatus(Status.valueOf("DRAFT"));
        postRepository.save(post);
        logger.debug("Review deleted successfully for post with post ID: {}", deleteReviewResponse.getPostId());
    }

    @Override
    public PostResponse submitPost(Long id) {
        logger.info("Submitting post with ID: {}", id);
        Post post = postRepository.findById(id).orElseThrow();
        post.setStatus(Status.valueOf("POSTED"));
        postRepository.save(post);

        logger.debug("Post submitted successfully with ID: {}", id);
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
