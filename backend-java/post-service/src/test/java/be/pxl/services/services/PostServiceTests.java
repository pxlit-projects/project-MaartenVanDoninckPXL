package be.pxl.services.services;

import be.pxl.services.PostServiceApp;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.DeleteReviewResponse;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.repository.PostRepository;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PostServiceApp.class)
@Testcontainers
@AutoConfigureMockMvc
public class PostServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IPostService postService;

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

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testCreatePost() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetPosts() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetDraftPosts() throws Exception {
        Post post = Post.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.DRAFT)
                .category(Category.EDUCATION)
                .createdOn(LocalDateTime.now())
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/draft"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testDraftPostsByAuthor() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.DRAFT)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/draft/author/Test author"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetApprovedPosts() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.APPROVED)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/approved"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetPendingPosts() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/pending"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void getPendingPostsByAuthor() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/pending/author/Test author"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetPostedPosts() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.POSTED)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/posted"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetAmountOfReviewedPostsByAuthor() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.APPROVED)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/reviewed/author/Test author"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testGetPostById() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/posts/1"));

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testUpdatePost() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.DRAFT)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        Long postId = postRepository.findAll().getFirst().getId();

        post.setTitle("Updated title");
        post.setContent("Updated content");
        post.setStatus(Status.PENDING);
        post.setCategory(Category.ENTERTAINMENT);

        String updatedPostJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(put("/api/posts/" + postId)
                        .contentType("application/json")
                        .content(updatedPostJson))
                .andExpect(status().isOk());

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testUpdatePostWithReview() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        Long postId = postRepository.findAll().getFirst().getId();

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .postId(postId)
                .reviewId(1L)
                .approval(true)
                .build();

        postService.updatePostWithReview(reviewRequest);

        Post updatedPost = postRepository.findById(postId).orElseThrow();

        assertEquals(Status.APPROVED, updatedPost.getStatus());
        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testUpdatePostWithDeleteReview() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(1L)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.PENDING)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        Long postId = postRepository.findAll().getFirst().getId();

        DeleteReviewResponse deleteReviewResponse = DeleteReviewResponse.builder()
                .postId(postId)
                .build();

        postService.updatePostWithDeletedReview(deleteReviewResponse);

        Post updatedPost = postRepository.findById(postId).orElseThrow();

        assertEquals(Status.DRAFT, updatedPost.getStatus());
        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testSubmitPost() throws Exception {
        PostRequest post = PostRequest.builder()
                .reviewId(null)
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(Status.APPROVED)
                .category(Category.EDUCATION)
                .build();

        String postJson = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isCreated());

        Long postId = postRepository.findAll().getFirst().getId();

        postService.submitPost(postId);

        Post updatedPost = postRepository.findById(postId).orElseThrow();

        assertEquals(Status.POSTED, updatedPost.getStatus());
        assertEquals(1, postRepository.findAll().size());
    }
}
