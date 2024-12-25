package be.pxl.services.domain.dto;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.PostResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostResponseTests {

    @Test
    public void testPostResponseBuilder() {
        LocalDateTime now = LocalDateTime.now();
        PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .reviewId(2L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.DRAFT)
                .category(Category.EDUCATION)
                .createdOn(now)
                .build();

        assertNotNull(postResponse);
        assertEquals(1L, postResponse.getId());
        assertEquals(2L, postResponse.getReviewId());
        assertEquals("Test title", postResponse.getTitle());
        assertEquals("Test content", postResponse.getContent());
        assertEquals("Test author", postResponse.getAuthor());
        assertEquals(Status.DRAFT, postResponse.getStatus());
        assertEquals(Category.EDUCATION, postResponse.getCategory());
        assertEquals(now, postResponse.getCreatedOn());
    }

    @Test
    public void testPostResponseSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        PostResponse postResponse = new PostResponse();
        postResponse.setId(1L);
        postResponse.setReviewId(2L);
        postResponse.setTitle("Test title");
        postResponse.setContent("Test content");
        postResponse.setAuthor("Test author");
        postResponse.setStatus(Status.DRAFT);
        postResponse.setCategory(Category.EDUCATION);
        postResponse.setCreatedOn(now);

        assertEquals(1L, postResponse.getId());
        assertEquals(2L, postResponse.getReviewId());
        assertEquals("Test title", postResponse.getTitle());
        assertEquals("Test content", postResponse.getContent());
        assertEquals("Test author", postResponse.getAuthor());
        assertEquals(Status.DRAFT, postResponse.getStatus());
        assertEquals(Category.EDUCATION, postResponse.getCategory());
        assertEquals(now, postResponse.getCreatedOn());
    }
}
