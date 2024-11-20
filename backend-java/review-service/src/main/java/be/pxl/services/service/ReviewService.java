package be.pxl.services.service;

import be.pxl.services.domain.Review;
import be.pxl.services.repository.IReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final IReviewRepository reviewRepository;

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}
