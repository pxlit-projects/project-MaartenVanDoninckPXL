package be.pxl.services.services;

import be.pxl.services.ReviewServiceApp;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.messaging.QueueService;
import be.pxl.services.repository.PostRepository;
import be.pxl.services.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ReviewServiceApp.class)
@Testcontainers
@AutoConfigureMockMvc
public class ReviewServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private QueueService queueService;

    @Container
    private static MySQLContainer<?> sqlContainer = new MySQLContainer<>("mysql:8.0.26");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setup() {
        reviewRepository.deleteAll();
        Mockito.reset(rabbitTemplate, queueService);
    }

    @Test
    @Transactional
    public void testCreateReview() throws Exception {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .postId(1L)
                .author("author")
                .approval(true)
                .content("content")
                .build();

        String postJson = objectMapper.writeValueAsString(reviewRequest);

        mockMvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void testGetReviews() throws Exception {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .postId(1L)
                .author("author")
                .approval(true)
                .content("content")
                .build();

        String postJson = objectMapper.writeValueAsString(reviewRequest);

        mockMvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());

        assertEquals(1, reviewService.getReviews().size());
    }

    @Test
    @Transactional
    public void testGetRejectionMessageById() throws Exception {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .postId(1L)
                .author("author")
                .approval(false)
                .content("content")
                .build();

        String postJson = objectMapper.writeValueAsString(reviewRequest);

        mockMvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        Long reviewId = reviewService.getReviews().getFirst().getReviewId();

        mockMvc.perform(get("/api/reviews/rejection/" + reviewId))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testDeleteReview() throws Exception {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .postId(1L)
                .author("author")
                .approval(true)
                .content("content")
                .build();

        String postJson = objectMapper.writeValueAsString(reviewRequest);

        mockMvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        Long reviewId = reviewService.getReviews().getFirst().getReviewId();

        mockMvc.perform(delete("/api/reviews/" + reviewId))
                .andExpect(status().isOk());

        assertEquals(0, reviewService.getReviews().size());
    }
}