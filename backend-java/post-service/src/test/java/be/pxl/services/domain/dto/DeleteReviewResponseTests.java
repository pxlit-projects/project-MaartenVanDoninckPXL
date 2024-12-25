package be.pxl.services.domain.dto;

import be.pxl.services.domain.dto.DeleteReviewResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteReviewResponseTests {

    @Test
    public void testDeleteReviewResponseBuilder() {
        DeleteReviewResponse response = DeleteReviewResponse.builder()
                .postId(1L)
                .build();

        assertNotNull(response);
        assertEquals(1L, response.getPostId());
    }

    @Test
    public void testDeleteReviewResponseSettersAndGetters() {
        DeleteReviewResponse response = new DeleteReviewResponse();
        response.setPostId(1L);

        assertEquals(1L, response.getPostId());
    }
}
