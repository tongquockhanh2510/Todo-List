package fit.backend.service;

import fit.backend.dto.request.TodoRequest;
import fit.backend.dto.response.TodoResponse;
import fit.backend.entity.Todo;
import fit.backend.mapper.TodoMapper;
import fit.backend.repository.TodoRepository;
import fit.backend.specification.TodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

        public Page<TodoResponse> getTodos(String keyword, String status, int page, int size, String sortBy, String direction) {
            Boolean completed = parseStatus(status);

            Sort sort = direction.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);

            Specification<Todo> spec = Specification
                    .where(TodoSpecification.hasKeyword(keyword))
                    .and(TodoSpecification.hasCompleted(completed));

            return todoRepository.findAll(spec, pageable)
                    .map(TodoMapper::toResponse);
        }

    public TodoResponse createTodo(TodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle().trim())
                .description(request.getDescription())
                .completed(false)
                .build();

        return TodoMapper.toResponse(todoRepository.save(todo));
    }

    public TodoResponse updateTodo(Long id, TodoRequest request) {
        Todo todo = getTodoById(id);

        todo.setTitle(request.getTitle().trim());
        todo.setDescription(request.getDescription());

        return TodoMapper.toResponse(todoRepository.save(todo));
    }

    public TodoResponse toggleTodo(Long id) {
        Todo todo = getTodoById(id);
        todo.setCompleted(!todo.getCompleted());

        return TodoMapper.toResponse(todoRepository.save(todo));
    }

    public void deleteTodo(Long id) {
        todoRepository.delete(getTodoById(id));
    }

    private Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    private Boolean parseStatus(String status) {
        if (status == null || status.equalsIgnoreCase("all")) {
            return null;
        }

        if (status.equalsIgnoreCase("completed")) {
            return true;
        }

        if (status.equalsIgnoreCase("pending")) {
            return false;
        }

        throw new RuntimeException("Invalid status. Use: all, completed, pending");
    }
}