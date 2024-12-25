package be.pxl.services.controller;

import be.pxl.services.ReviewServiceApp;
import be.pxl.services.domain.dto.RejectionResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.domain.dto.ReviewResponse;
import be.pxl.services.services.IReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ReviewServiceApp.class)
@AutoConfigureMockMvc
public class ReviewControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void createReview_createsReviewSuccessfully() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest(1L, "Test author", true, "Test content");

        mockMvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void getReviews_returnsListOfReviews() throws Exception {
        List<ReviewResponse> reviewResponses = List.of(new ReviewResponse(1L, 1L, true, "Test author", "Test content"));

        when(reviewService.getReviews()).thenReturn(reviewResponses);

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());

        assertEquals(reviewResponses, reviewService.getReviews());
    }

    @Test
    void getRejectionMessageById_returnsRejectionMessage() throws Exception {
        RejectionResponse rejectionResponse = new RejectionResponse("Test author", "Test content");

        when(reviewService.getRejectionMessageById(1L)).thenReturn(rejectionResponse);

        mockMvc.perform(get("/api/reviews/rejection/1"))
                .andExpect(status().isOk());

        assertEquals(rejectionResponse, reviewService.getRejectionMessageById(1L));
    }

    @Test
    void deleteReview_deletesReviewSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isOk());
    }
}
