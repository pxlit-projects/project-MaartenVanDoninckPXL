package be.pxl.services.domain.dto;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.PostRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostRequestTests {

    @Test
    public void testPostRequestBuilder() {
        PostRequest postRequest = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        assertNotNull(postRequest);
        assertEquals(1L, postRequest.getReviewId());
        assertEquals("Test title", postRequest.getTitle());
        assertEquals("Test content", postRequest.getContent());
        assertEquals("Test author", postRequest.getAuthor());
        assertEquals(Status.PENDING, postRequest.getStatus());
        assertEquals(Category.EDUCATION, postRequest.getCategory());
    }

    @Test
    public void testPostRequestSettersAndGetters() {
        PostRequest postRequest = new PostRequest();
        postRequest.setReviewId(1L);
        postRequest.setTitle("Test title");
        postRequest.setContent("Test content");
        postRequest.setAuthor("Test author");
        postRequest.setStatus(Status.PENDING);
        postRequest.setCategory(Category.EDUCATION);

        assertEquals(1L, postRequest.getReviewId());
        assertEquals("Test title", postRequest.getTitle());
        assertEquals("Test content", postRequest.getContent());
        assertEquals("Test author", postRequest.getAuthor());
        assertEquals(Status.PENDING, postRequest.getStatus());
        assertEquals(Category.EDUCATION, postRequest.getCategory());
    }
}
