package be.pxl.services.services;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.DeleteReviewResponse;
import be.pxl.services.domain.dto.RejectionResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final RabbitTemplate rabbitTemplate;

    public void createReview(ReviewRequest reviewRequest) {
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

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .postId(review.getPostId())
                .reviewId(review.getId())
                .approval(review.isApproval())
                .author(review.getAuthor())
                .content(review.getContent())
                .build();

        rabbitTemplate.convertAndSend("post-service-queue", reviewResponse);
    }

    public List<ReviewResponse> getReviews() {
        return reviewRepository.findAll().stream().map(review -> ReviewResponse.builder()
                .postId(review.getPostId())
                .reviewId(review.getId())
                .approval(review.isApproval())
                .build()).toList();
    }

    @Override
    public RejectionResponse getRejectionMessageById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " not found");
        }
        Review review = reviewOptional.get();
        if (review.isApproval()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " is approved");
        }
        return RejectionResponse.builder()
                .author(review.getAuthor())
                .content(review.getContent())
                .build();
    }

    @Override
    public void deleteReview(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new IllegalArgumentException("Review with id " + reviewId + " not found");
        }
        Review review = reviewOptional.get();
        reviewRepository.deleteById(reviewId);

        DeleteReviewResponse deleteReviewResponse = DeleteReviewResponse.builder()
                .postId(review.getPostId())
                .build();

        rabbitTemplate.convertAndSend("post-service-queue", deleteReviewResponse);
    }
}
