package be.pxl.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import be.pxl.services.repository.PostRepository;
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

@SpringBootTest(classes = PostServiceApp.class)
@Testcontainers
@AutoConfigureMockMvc
public class PostTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;

    @Container
    private static MySQLContainer<?> sqlContainer = new MySQLContainer<>("mysql:8.0.26");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    public void testCreatePost() throws Exception {
        Post post = Post.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        assertEquals(1, postRepository.findAll().size());
    }
}
