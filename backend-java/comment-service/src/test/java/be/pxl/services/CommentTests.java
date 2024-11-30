package be.pxl.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.repository.CommentRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = CommentServiceApp.class)
@Testcontainers
@AutoConfigureMockMvc
public class CommentTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;

    @Container
    private static MySQLContainer<?> sqlContainer = new MySQLContainer<>("mysql:8.0.26");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    public void testCreateComment() throws Exception {
        Comment comment = Comment.builder()
                .postId(1L)
                .content("Test content")
                .author("Test author")
                .build();

        String commentJson = objectMapper.writeValueAsString(comment);

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(commentJson))
                .andExpect(status().isCreated());

        assertEquals(1, commentRepository.findAll().size());
    }
}
