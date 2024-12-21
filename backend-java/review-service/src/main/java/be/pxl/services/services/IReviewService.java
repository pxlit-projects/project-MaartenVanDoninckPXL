package be.pxl.services.services;

import be.pxl.services.domain.dto.RejectionResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;

import java.util.List;

public interface IReviewService {

    void createReview(ReviewRequest reviewRequest);
    List<ReviewResponse> getReviews();
    RejectionResponse getRejectionMessageById(Long reviewId);
    void deleteReview(Long reviewId);
}
