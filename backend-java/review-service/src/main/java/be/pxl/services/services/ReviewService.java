package be.pxl.services.services;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final RabbitTemplate rabbitTemplate;

    public List<ReviewResponse> getReviews() {
        return reviewRepository.findAll().stream().map(review -> ReviewResponse.builder()
                .postId(review.getPostId())
                .postId(review.getId())
                .approval(review.getApproval())
                .build()).toList();
    }

    public void createReview(ReviewRequest reviewRequest) {
        Review review = Review.builder()
                .postId(reviewRequest.getPostId())
                .author(reviewRequest.getAuthor())
                .approval(reviewRequest.getApproval())
                .build();
        reviewRepository.save(review);

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .postId(review.getPostId())
                .reviewId(review.getId())
                .approval(review.getApproval())
                .build();

        rabbitTemplate.convertAndSend("post-service-queue", reviewResponse);
    }
}
