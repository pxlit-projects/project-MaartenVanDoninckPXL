package be.pxl.services.domain.dto;

import be.pxl.services.domain.dto.ReviewRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewRequestTests {

    @Test
    public void testReviewRequestBuilder() {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .postId(1L)
                .reviewId(2L)
                .approval(true)
                .build();

        assertNotNull(reviewRequest);
        assertEquals(1L, reviewRequest.getPostId());
        assertEquals(2L, reviewRequest.getReviewId());
        assertTrue(reviewRequest.isApproval());
    }

    @Test
    public void testReviewRequestSettersAndGetters() {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setPostId(1L);
        reviewRequest.setReviewId(2L);
        reviewRequest.setApproval(true);

        assertEquals(1L, reviewRequest.getPostId());
        assertEquals(2L, reviewRequest.getReviewId());
        assertTrue(reviewRequest.isApproval());
    }
}
