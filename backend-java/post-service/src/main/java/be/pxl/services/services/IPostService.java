package be.pxl.services.services;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.ReviewRequest;

import java.util.List;

public interface IPostService {
    PostResponse createPost(PostRequest postRequest);
    List<PostResponse> getPosts();
    List<PostResponse> getDraftPosts();
    List<PostResponse> getApprovedPosts();
    List<PostResponse> getDraftPostsByAuthor(String author);
    List<PostResponse> getPendingPosts();
    List<PostResponse> getPendingPostsByAuthor(String author);
    List<PostResponse> getPostedPosts();
    PostResponse getPostById(Long id);
    PostResponse updatePost(Long id, PostRequest postRequest);
    void updatePostWithReview(ReviewRequest reviewRequest);
    PostResponse approvePost(Long id);
    PostResponse rejectPost(Long id);
    PostResponse submitPost(Long id);
}
