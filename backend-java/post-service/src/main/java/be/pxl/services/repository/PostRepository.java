package be.pxl.services.repository;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatus(Status status);
    List<Post> findAllByStatusAndAuthor(Status draft, String author);
    List<Post> findAllByStatusIn(List<Status> statuses);
    List<Post> findAllByStatusInAndAuthor(List<Status> statuses, String author);
}
