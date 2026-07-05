package fit.backend.controller;

import fit.backend.dto.request.TodoRequest;
import fit.backend.entity.Todo;
import fit.backend.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;
    @GetMapping
    public List<Todo> getTodos(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "all") String status
    ) {
        return todoService.getTodos(keyword, status);
    }

    @PostMapping
    public Todo createTodo(@Valid @RequestBody TodoRequest request) {
        return todoService.createTodo(request);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request){
        return todoService.updateTodo(id,request);
    }
    @PatchMapping("/{id}/toggle")
    public Todo toggleTodo(@PathVariable Long id) {
        return todoService.toggleTodo(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}
