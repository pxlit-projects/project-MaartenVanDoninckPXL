package be.pxl.services.services;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.DeleteReviewResponse;
import be.pxl.services.domain.dto.RejectionResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    private final RabbitTemplate rabbitTemplate;

    public void createReview(ReviewRequest reviewRequest) {
        logger.info("Creating review for post with id {}", reviewRequest.getPostId());
        Optional<Review> reviewOptional = reviewRepository.findReviewsByPostId(reviewRequest.getPostId());
        if (reviewOptional.isPresent()) {
            throw new IllegalArgumentException("Review already exists for post with id " + reviewRequest.getPostId());
        }
        Review review = Review.builder()
                .postId(reviewRequest.getPostId())
                .author(reviewRequest.getAuthor())
                .approval(reviewRequest.isApproval())
                .content(reviewRequest.getContent())
                .build();
        reviewRepository.save(review);
        logger.info("Review created for post with id {}", reviewRequest.getPostId());

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .postId(review.getPostId())
                .reviewId(review.getId())
                .approval(review.isApproval())
                .author(review.getAuthor())
                .content(review.getContent())
                .build();

        logger.info("Sending review to post-service-create-queue");
        rabbitTemplate.convertAndSend("post-service-create-queue", reviewResponse);
    }

    public List<ReviewResponse> getReviews() {
        logger.info("Getting all reviews");
        return reviewRepository.findAll().stream().map(review -> ReviewResponse.builder()
                .postId(review.getPostId())
                .reviewId(review.getId())
                .approval(review.isApproval())
                .build()).toList();
    }

    @Override
    public RejectionResponse getRejectionMessageById(Long reviewId) {
        logger.info("Getting rejection message for review with id {}", reviewId);
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " not found");
        }
        Review review = reviewOptional.get();
        if (review.isApproval()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " is approved");
        }

        logger.info("Returning rejection message for review with id {}", reviewId);
        return RejectionResponse.builder()
                .author(review.getAuthor())
                .content(review.getContent())
                .build();
    }

    @Override
    public void deleteReview(Long reviewId) {
        logger.info("Deleting review with id {}", reviewId);
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " not found");
        }
        Review review = reviewOptional.get();
        reviewRepository.deleteById(reviewId);

        logger.info("Review with id {} deleted", reviewId);
        DeleteReviewResponse deleteReviewResponse = DeleteReviewResponse.builder()
                .postId(review.getPostId())
                .build();

        logger.info("Sending delete review to post-service-delete-queue");
        rabbitTemplate.convertAndSend("post-service-delete-queue", deleteReviewResponse);
    }
}
