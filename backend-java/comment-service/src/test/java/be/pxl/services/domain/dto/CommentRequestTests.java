package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentRequestTests {

    @Test
    public void testCommentRequestBuilder() {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("Test content")
                .author("Test author")
                .build();

        assertThat(commentRequest.getPostId()).isEqualTo(1L);
        assertThat(commentRequest.getContent()).isEqualTo("Test content");
        assertThat(commentRequest.getAuthor()).isEqualTo("Test author");
    }

    @Test
    public void testCommentRequestSettersAndGetters() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPostId(1L);
        commentRequest.setContent("Test content");
        commentRequest.setAuthor("Test author");

        assertThat(commentRequest.getPostId()).isEqualTo(1L);
        assertThat(commentRequest.getContent()).isEqualTo("Test content");
        assertThat(commentRequest.getAuthor()).isEqualTo("Test author");
    }

    @Test
    public void testCommentRequestNoArgsConstructor() {
        CommentRequest commentRequest = new CommentRequest();

        assertThat(commentRequest.getPostId()).isNull();
        assertThat(commentRequest.getContent()).isNull();
        assertThat(commentRequest.getAuthor()).isNull();
    }

    @Test
    public void testCommentRequestAllArgsConstructor() {
        CommentRequest commentRequest = new CommentRequest(1L, "Test content", "Test author");

        assertThat(commentRequest.getPostId()).isEqualTo(1L);
        assertThat(commentRequest.getContent()).isEqualTo("Test content");
        assertThat(commentRequest.getAuthor()).isEqualTo("Test author");
    }
}
