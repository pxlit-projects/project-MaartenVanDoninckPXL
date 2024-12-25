package be.pxl.services.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RejectionResponseTests {

    @Test
    public void testRejectionResponseBuilder() {
        RejectionResponse response = RejectionResponse.builder()
                .author("author")
                .content("content")
                .build();

        assertEquals("author", response.getAuthor());
        assertEquals("content", response.getContent());
    }

    @Test
    public void testRejectionResponseSettersAndGetters() {
        RejectionResponse response = new RejectionResponse();
        response.setAuthor("author");
        response.setContent("content");

        assertEquals("author", response.getAuthor());
        assertEquals("content", response.getContent());
    }

    @Test
    public void testRejectionResponseNoArgsConstructor() {
        RejectionResponse response = new RejectionResponse();
        assertNull(response.getAuthor());
        assertNull(response.getContent());
    }

    @Test
    public void testRejectionResponseAllArgsConstructor() {
        RejectionResponse response = new RejectionResponse("author", "content");
        assertEquals("author", response.getAuthor());
        assertEquals("content", response.getContent());
    }
}
