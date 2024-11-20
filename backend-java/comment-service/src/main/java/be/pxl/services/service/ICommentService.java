package be.pxl.services.service;

import be.pxl.services.domain.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getComments();
}
