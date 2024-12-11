package be.pxl.services.services;

import be.pxl.services.domain.dto.PostRequest;
import be.pxl.services.domain.dto.PostResponse;

import java.util.List;

public interface IPostService {
    List<PostResponse> getPosts();
    List<PostResponse> getDraftPosts();
    List<PostResponse> getApprovedPosts();
    PostResponse createPost(PostRequest postRequest);
}
