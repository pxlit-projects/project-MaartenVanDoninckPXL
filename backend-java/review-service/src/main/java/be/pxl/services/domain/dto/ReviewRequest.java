package be.pxl.services.domain.dto;

import be.pxl.services.domain.Approval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long postId;
    private String author;
    private Approval approval;
}
