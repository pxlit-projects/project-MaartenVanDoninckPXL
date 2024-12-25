package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewResponseTests {

    @Test
    public void testReviewResponseBuilder() {
        ReviewResponse response = ReviewResponse.builder()
                .postId(1L)
                .reviewId(1L)
                .author("author")
                .approval(true)
                .content("content")
                .build();

        assertEquals(1L, response.getPostId());
        assertEquals(1L, response.getReviewId());
        assertEquals("author", response.getAuthor());
        assertTrue(response.isApproval());
        assertEquals("content", response.getContent());
    }

    @Test
    public void testReviewResponseSettersAndGetters() {
        ReviewResponse response = new ReviewResponse();
        response.setPostId(1L);
        response.setReviewId(1L);
        response.setAuthor("author");
        response.setApproval(true);
        response.setContent("content");

        assertEquals(1L, response.getPostId());
        assertEquals(1L, response.getReviewId());
        assertEquals("author", response.getAuthor());
        assertTrue(response.isApproval());
        assertEquals("content", response.getContent());
    }

    @Test
    public void testReviewResponseNoArgsConstructor() {
        ReviewResponse response = new ReviewResponse();
        assertNull(response.getPostId());
        assertNull(response.getReviewId());
        assertNull(response.getAuthor());
        assertFalse(response.isApproval());
        assertNull(response.getContent());
    }

    @Test
    public void testReviewResponseAllArgsConstructor() {
        ReviewResponse response = new ReviewResponse(1L, 1L, true, "author", "content");
        assertEquals(1L, response.getPostId());
        assertEquals(1L, response.getReviewId());
        assertEquals("author", response.getAuthor());
        assertTrue(response.isApproval());
        assertEquals("content", response.getContent());
    }
}
