package be.pxl.services.controller;

import be.pxl.services.CommentServiceApp;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.services.ICommentService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CommentServiceApp.class)
@AutoConfigureMockMvc
public class CommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ICommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void createComment_createsCommentSuccessfully() throws Exception {
        CommentRequest commentRequest = new CommentRequest(1L, "Test author", "Test content");

        mockMvc.perform(post("/api/comments")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void getComments_returnsListOfComments() throws Exception {
        List<CommentResponse> commentResponses = List.of(new CommentResponse(1L, 1L, "Test author", "Test content"));

        when(commentService.getComments()).thenReturn(commentResponses);

        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk());

        assertEquals(commentResponses, commentService.getComments());
    }

    @Test
    void getCommentsByPostId_returnsListOfComments() throws Exception {
        List<CommentResponse> commentResponses = List.of(new CommentResponse(1L, 1L, "Test author", "Test content"));

        when(commentService.getCommentsByPostId(1L)).thenReturn(commentResponses);

        mockMvc.perform(get("/api/comments/1"))
                .andExpect(status().isOk());

        assertEquals(commentResponses, commentService.getCommentsByPostId(1L));
    }

    @Test
    void updateComment_updatesCommentSuccessfully() throws Exception {
        CommentRequest commentRequest = new CommentRequest(1L, "Test author", "Test content");

        mockMvc.perform(patch("/api/comments/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isOk());

        verify(commentService).updateComment(1L, commentRequest);
    }

    @Test
    void deleteComment_deletesCommentSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isOk());

        verify(commentService).deleteComment(1L);
    }
}
