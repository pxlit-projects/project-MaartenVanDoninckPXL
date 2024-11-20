package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final IPostRepository postRepository;

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }
}
