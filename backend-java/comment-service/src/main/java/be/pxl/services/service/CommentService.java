package be.pxl.services.service;

import be.pxl.services.domain.Comment;
import be.pxl.services.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final ICommentRepository commentRepository;

    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }
}
