package be.pxl.services.services;

import be.pxl.services.domain.Review;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;
import be.pxl.services.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getReviews() {
        return reviewRepository.findAll().stream().map(review -> ReviewResponse.builder()
                .postId(review.getPostId())
                .author(review.getAuthor())
                .content(review.getContent())
                .build()).toList();
    }

    public void createReview(ReviewRequest reviewRequest) {
        Review review = Review.builder()
                .postId(reviewRequest.getPostId())
                .author(reviewRequest.getAuthor())
                .content(reviewRequest.getContent())
                .build();
        reviewRepository.save(review);
    }
}
