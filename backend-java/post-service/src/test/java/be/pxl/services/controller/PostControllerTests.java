package be.pxl.services.controller;

import be.pxl.services.PostServiceApp;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Status;
import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.services.IPostService;
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

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PostServiceApp.class)
@AutoConfigureMockMvc
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IPostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void createPost_createsPostSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.DRAFT, Category.EDUCATION, LocalDateTime.now());
        when(postService.createPost(any())).thenReturn(postResponse);

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postResponse)))
                .andExpect(status().isCreated());

        verify(postService, times(1)).createPost(any());
    }

    @Test
    void getPosts_returnsPostsSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.DRAFT, Category.EDUCATION, LocalDateTime.now());
        when(postService.getPosts()).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getPosts();
    }

    @Test
    void getDraftPosts_returnsDraftPostsSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.DRAFT, Category.EDUCATION, LocalDateTime.now());
        when(postService.getDraftPosts()).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/draft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getDraftPosts();
    }

    @Test
    void getDraftPostsByAuthor_returnsDraftPostsByAuthorSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.DRAFT, Category.EDUCATION, LocalDateTime.now());
        when(postService.getDraftPostsByAuthor(anyString())).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/draft/Test author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getDraftPostsByAuthor(anyString());
    }

    @Test
    void getPendingPosts_returnsPendingPostsSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.PENDING, Category.EDUCATION, LocalDateTime.now());
        when(postService.getPendingPosts()).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getPendingPosts();
    }

    @Test
    void getPendingPostsByAuthor_returnsPendingPostsByAuthorSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.PENDING, Category.EDUCATION, LocalDateTime.now());
        when(postService.getPendingPostsByAuthor(anyString())).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/pending/Test author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getPendingPostsByAuthor(anyString());
    }

    @Test
    void getApprovedPosts_returnsApprovedPostsSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.APPROVED, Category.EDUCATION, LocalDateTime.now());
        when(postService.getApprovedPosts()).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getApprovedPosts();
    }

    @Test
    void getPostedPosts_returnsPostedPostsSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.POSTED, Category.EDUCATION, LocalDateTime.now());
        when(postService.getPostedPosts()).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/posted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponse.getId()));

        verify(postService, times(1)).getPostedPosts();
    }

    @Test
    void getPostById_returnsPostByIdSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.DRAFT, Category.EDUCATION, LocalDateTime.now());
        when(postService.getPostById(anyLong())).thenReturn(postResponse);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postResponse.getId()));

        verify(postService, times(1)).getPostById(anyLong());
    }

    @Test
    void getAmountOfReviewedPostsByAuthor_returnsAmountOfReviewedPostsByAuthorSuccessfully() throws Exception {
        when(postService.getAmountOfReviewedPostsByAuthor(anyString())).thenReturn(5);

        mockMvc.perform(get("/api/posts/reviewed/Test author"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(postService, times(1)).getAmountOfReviewedPostsByAuthor(anyString());
    }

    @Test
    void updatePost_updatesPostSuccessfully() throws Exception {
        PostRequest postRequest = new PostRequest(1L, "Updated title", "Updated content", "Updated author", Status.DRAFT, Category.EDUCATION);
        PostResponse postResponse = new PostResponse(1L, 1L, "Updated title", "Updated content", "Updated author", Status.DRAFT, Category.EDUCATION, LocalDateTime.now());
        when(postService.updatePost(anyLong(), any())).thenReturn(postResponse);

        mockMvc.perform(put("/api/posts/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postResponse.getId()));

        verify(postService, times(1)).updatePost(anyLong(), any());
    }

    @Test
    void submitPost_submitsPostSuccessfully() throws Exception {
        PostResponse postResponse = new PostResponse(1L, 1L, "Test title", "Test content", "Test author", Status.POSTED, Category.EDUCATION, LocalDateTime.now());
        when(postService.submitPost(anyLong())).thenReturn(postResponse);

        mockMvc.perform(patch("/api/posts/submit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postResponse.getId()));

        verify(postService, times(1)).submitPost(anyLong());
    }
}
