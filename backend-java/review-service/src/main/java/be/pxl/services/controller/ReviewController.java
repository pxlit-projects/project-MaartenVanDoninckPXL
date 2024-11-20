package be.pxl.services.controller;

import be.pxl.services.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getReviews() {
        return new ResponseEntity<>(reviewService.getReviews(), HttpStatus.OK);
    }
}
