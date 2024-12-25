package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteReviewResponseTests {

    @Test
    public void testDeleteReviewResponseBuilder() {
        DeleteReviewResponse response = DeleteReviewResponse.builder()
                .postId(1L)
                .build();

        assertEquals(1L, response.getPostId());
    }

    @Test
    public void testDeleteReviewResponseSettersAndGetters() {
        DeleteReviewResponse response = new DeleteReviewResponse();
        response.setPostId(1L);

        assertEquals(1L, response.getPostId());
    }

    @Test
    public void testDeleteReviewResponseNoArgsConstructor() {
        DeleteReviewResponse response = new DeleteReviewResponse();
        assertNull(response.getPostId());
    }

    @Test
    public void testDeleteReviewResponseAllArgsConstructor() {
        DeleteReviewResponse response = new DeleteReviewResponse(1L);
        assertEquals(1L, response.getPostId());
    }
}
