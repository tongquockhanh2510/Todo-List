package fit.backend.service;

import fit.backend.dto.request.TodoRequest;
import fit.backend.entity.Todo;
import fit.backend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public List<Todo> getTodos(String title) {
        if (title != null && !title.isEmpty()) {
            return todoRepository.findByTitleContainingIgnoreCase(title);
        } else {
            return todoRepository.findAll();
        }
    }

    public Todo createTodo(TodoRequest todoRequest){
        Todo todo = Todo.builder()
                .title(todoRequest.getTitle())
                .description(todoRequest.getDescription())
                .completed(false)
                .build();
        return todoRepository.save(todo);
    }

    private Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    public Todo updateTodo(Long id, TodoRequest todoRequest){
        Todo todo = getTodoById(id);
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        Todo todo = getTodoById(id);
        todoRepository.delete(todo);
    }

    public Todo toggleTodo(Long id) {
        Todo todo = getTodoById(id);
        todo.setCompleted(!todo.getCompleted());

        return todoRepository.save(todo);
    }
}
