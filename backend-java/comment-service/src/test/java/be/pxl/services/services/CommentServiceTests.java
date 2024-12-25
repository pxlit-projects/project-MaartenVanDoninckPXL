package be.pxl.services.services;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
public class CommentServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ICommentService commentService;

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
        commentRepository.deleteAll();
    }

    @Test
    public void testCreateComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("content")
                .author("author")
                .build();

        String json = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        assertEquals(1, commentRepository.findAll().size());
    }

    @Test
    public void testGetComments() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("content")
                .author("author")
                .build();

        String json = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk());

        assertEquals(1, commentRepository.findAll().size());
    }

    @Test
    public void testGetCommentsByPostId() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("content")
                .author("author")
                .build();

        String json = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk());

        assertEquals(1, commentRepository.findAll().size());
    }

    @Test
    public void testUpdateComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("content")
                .author("author")
                .build();

        String json = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        Long id = commentRepository.findAll().getFirst().getId();

        CommentRequest updatedCommentRequest = CommentRequest.builder()
                .postId(1L)
                .content("updated content")
                .author("author")
                .build();

        json = objectMapper.writeValueAsString(updatedCommentRequest);

        mockMvc.perform(patch("/api/comments/" + id)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());

        assertEquals("updated content", commentRepository.findById(id).orElseThrow().getContent());
    }

    @Test
    public void testDeleteComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("content")
                .author("author")
                .build();

        String json = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        Long id = commentRepository.findAll().getFirst().getId();

        mockMvc.perform(delete("/api/comments/" + id))
                .andExpect(status().isOk());

        assertEquals(0, commentRepository.findAll().size());
    }
}
