package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTests {

    @Test
    public void testCommentBuilder() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .author("Test author")
                .build();

        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("Test content");
        assertThat(comment.getAuthor()).isEqualTo("Test author");
    }

    @Test
    public void testCommentSettersAndGetters() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setContent("Test content");
        comment.setAuthor("Test author");

        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("Test content");
        assertThat(comment.getAuthor()).isEqualTo("Test author");
    }

    @Test
    public void testCommentNoArgsConstructor() {
        Comment comment = new Comment();

        assertThat(comment.getId()).isNull();
        assertThat(comment.getPostId()).isNull();
        assertThat(comment.getContent()).isNull();
        assertThat(comment.getAuthor()).isNull();
    }

    @Test
    public void testCommentAllArgsConstructor() {
        Comment comment = new Comment(1L, 1L, "Test content", "Test author");

        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("Test content");
        assertThat(comment.getAuthor()).isEqualTo("Test author");
    }
}
