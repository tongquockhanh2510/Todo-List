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
    public List<Todo> getTodos(String title) {
        return todoService.getTodos(title);
    }

    @PostMapping
    public Todo createTodo(@Valid @RequestBody TodoRequest request) {
        return todoService.createTodo(request);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request){
        return todoService.updateTodo(id,request);
    }

}
