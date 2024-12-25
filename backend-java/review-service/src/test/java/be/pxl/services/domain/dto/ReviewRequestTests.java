package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewRequestTests {

    @Test
    public void testReviewRequestBuilder() {
        ReviewRequest request = ReviewRequest.builder()
                .postId(1L)
                .author("author")
                .approval(false)
                .content("content")
                .build();

        assertEquals(1L, request.getPostId());
        assertEquals("author", request.getAuthor());
        assertFalse(request.isApproval());
        assertEquals("content", request.getContent());
    }

    @Test
    public void testReviewRequestSettersAndGetters() {
        ReviewRequest request = new ReviewRequest();
        request.setPostId(1L);
        request.setAuthor("author");
        request.setApproval(false);
        request.setContent("content");

        assertEquals(1L, request.getPostId());
        assertEquals("author", request.getAuthor());
        assertFalse(request.isApproval());
        assertEquals("content", request.getContent());
    }

    @Test
    public void testReviewRequestNoArgsConstructor() {
        ReviewRequest request = new ReviewRequest();
        assertNull(request.getPostId());
        assertNull(request.getAuthor());
        assertFalse(request.isApproval());
        assertNull(request.getContent());
    }

    @Test
    public void testReviewRequestAllArgsConstructor() {
        ReviewRequest request = new ReviewRequest(1L, "author", false, "content");
        assertEquals(1L, request.getPostId());
        assertEquals("author", request.getAuthor());
        assertFalse(request.isApproval());
        assertEquals("content", request.getContent());
    }
}
