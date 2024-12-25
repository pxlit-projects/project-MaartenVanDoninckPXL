package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentResponseTests {

    @Test
    public void testCommentResponseBuilder() {
        CommentResponse commentResponse = CommentResponse.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .author("Test author")
                .build();

        assertThat(commentResponse.getId()).isEqualTo(1L);
        assertThat(commentResponse.getPostId()).isEqualTo(1L);
        assertThat(commentResponse.getContent()).isEqualTo("Test content");
        assertThat(commentResponse.getAuthor()).isEqualTo("Test author");
    }

    @Test
    public void testCommentResponseSettersAndGetters() {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(1L);
        commentResponse.setPostId(1L);
        commentResponse.setContent("Test content");
        commentResponse.setAuthor("Test author");

        assertThat(commentResponse.getId()).isEqualTo(1L);
        assertThat(commentResponse.getPostId()).isEqualTo(1L);
        assertThat(commentResponse.getContent()).isEqualTo("Test content");
        assertThat(commentResponse.getAuthor()).isEqualTo("Test author");
    }

    @Test
    public void testCommentResponseNoArgsConstructor() {
        CommentResponse commentResponse = new CommentResponse();

        assertThat(commentResponse.getId()).isNull();
        assertThat(commentResponse.getPostId()).isNull();
        assertThat(commentResponse.getContent()).isNull();
        assertThat(commentResponse.getAuthor()).isNull();
    }

    @Test
    public void testCommentResponseAllArgsConstructor() {
        CommentResponse commentResponse = new CommentResponse(1L, 1L, "Test content", "Test author");

        assertThat(commentResponse.getId()).isEqualTo(1L);
        assertThat(commentResponse.getPostId()).isEqualTo(1L);
        assertThat(commentResponse.getContent()).isEqualTo("Test content");
        assertThat(commentResponse.getAuthor()).isEqualTo("Test author");
    }
}
