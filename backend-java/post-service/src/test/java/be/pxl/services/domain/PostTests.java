package be.pxl.services.domain;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostTests {

    @Test
    public void testPostBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Post post = Post.builder()
                .id(1L)
                .reviewId(2L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.DRAFT)
                .category(Category.EDUCATION)
                .createdOn(now)
                .build();

        assertNotNull(post);
        assertEquals(1L, post.getId());
        assertEquals(2L, post.getReviewId());
        assertEquals("Test title", post.getTitle());
        assertEquals("Test content", post.getContent());
        assertEquals("Test author", post.getAuthor());
        assertEquals(Status.DRAFT, post.getStatus());
        assertEquals(Category.EDUCATION, post.getCategory());
        assertEquals(now, post.getCreatedOn());
    }

    @Test
    public void testPostSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setId(1L);
        post.setReviewId(2L);
        post.setTitle("Test title");
        post.setContent("Test content");
        post.setAuthor("Test author");
        post.setStatus(Status.DRAFT);
        post.setCategory(Category.EDUCATION);
        post.setCreatedOn(now);

        assertEquals(1L, post.getId());
        assertEquals(2L, post.getReviewId());
        assertEquals("Test title", post.getTitle());
        assertEquals("Test content", post.getContent());
        assertEquals("Test author", post.getAuthor());
        assertEquals(Status.DRAFT, post.getStatus());
        assertEquals(Category.EDUCATION, post.getCategory());
        assertEquals(now, post.getCreatedOn());
    }
}
