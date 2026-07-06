package fit.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
