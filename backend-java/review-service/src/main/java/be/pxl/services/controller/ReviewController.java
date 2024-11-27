package be.pxl.services.controller;

import be.pxl.services.domain.Review;
import be.pxl.services.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews() {
        return new ResponseEntity<>(reviewService.getReviews(), HttpStatus.OK);
    }
}
