package be.pxl.services.controller;

import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;
import be.pxl.services.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews() {
        return new ResponseEntity<>(reviewService.getReviews(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            reviewService.createReview(reviewRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
