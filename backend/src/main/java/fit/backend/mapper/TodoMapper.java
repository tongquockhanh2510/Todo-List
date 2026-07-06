package fit.backend.mapper;




import fit.backend.dto.response.TodoResponse;
import fit.backend.entity.Todo;

public class TodoMapper {

    public static TodoResponse toResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.getCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
