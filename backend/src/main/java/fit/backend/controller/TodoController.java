package fit.backend.controller;

import fit.backend.dto.request.TodoRequest;
import fit.backend.dto.response.TodoResponse;
import fit.backend.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public Page<TodoResponse> getTodos(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "all") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return todoService.getTodos(keyword, status, page, size, sortBy, direction);
    }

    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest request) {
        return todoService.createTodo(request);
    }

    @PutMapping("/{id}")
    public TodoResponse updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request
    ) {
        return todoService.updateTodo(id, request);
    }

    @PatchMapping("/{id}/toggle")
    public TodoResponse toggleTodo(@PathVariable Long id) {
        return todoService.toggleTodo(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}