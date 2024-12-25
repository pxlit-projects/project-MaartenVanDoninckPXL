package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTests {

    @Test
    public void testReviewBuilder() {
        Review review = Review.builder()
                .id(1L)
                .postId(1L)
                .author("author")
                .content("content")
                .approval(true)
                .build();

        assertEquals(1L, review.getId());
        assertEquals(1L, review.getPostId());
        assertEquals("author", review.getAuthor());
        assertEquals("content", review.getContent());
        assertTrue(review.isApproval());
    }

    @Test
    public void testReviewSettersAndGetters() {
        Review review = new Review();
        review.setId(1L);
        review.setPostId(1L);
        review.setAuthor("author");
        review.setContent("content");
        review.setApproval(true);

        assertEquals(1L, review.getId());
        assertEquals(1L, review.getPostId());
        assertEquals("author", review.getAuthor());
        assertEquals("content", review.getContent());
        assertTrue(review.isApproval());
    }

    @Test
    public void testReviewNoArgsConstructor() {
        Review review = new Review();
        assertNull(review.getId());
        assertNull(review.getPostId());
        assertNull(review.getAuthor());
        assertNull(review.getContent());
        assertFalse(review.isApproval());
    }

    @Test
    public void testReviewAllArgsConstructor() {
        Review review = new Review(1L, 1L, "author", "content", true);
        assertEquals(1L, review.getId());
        assertEquals(1L, review.getPostId());
        assertEquals("author", review.getAuthor());
        assertEquals("content", review.getContent());
        assertTrue(review.isApproval());
    }
}
