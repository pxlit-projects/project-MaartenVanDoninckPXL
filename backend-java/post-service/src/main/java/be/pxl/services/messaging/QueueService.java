package be.pxl.services.messaging;

import be.pxl.services.domain.dto.DeleteReviewResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final IPostService postService;

    @RabbitListener(queues = "post-service-create-queue")
    public void listen(ReviewRequest reviewRequest) {
        postService.updatePostWithReview(reviewRequest);
    }

    @RabbitListener(queues = "post-service-delete-queue")
    public void listen(DeleteReviewResponse deleteReviewResponse) {
        postService.updatePostWithDeletedReview(deleteReviewResponse);
    }
}
