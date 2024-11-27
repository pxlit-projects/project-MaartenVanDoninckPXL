package be.pxl.services.domain.dto;

import be.pxl.services.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long reviewId;
    private String title;
    private String content;
    private String author;
    private Status status;
}
