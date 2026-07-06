package fit.backend.service;

import fit.backend.dto.request.TodoRequest;
import fit.backend.dto.response.TodoResponse;
import fit.backend.entity.Todo;
import fit.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldCreateTodoSuccessfully(){
        TodoRequest request = new TodoRequest();
        request.setTitle("Test Todo");
        request.setDescription("This is a test todo");

        Todo savedTodo = Todo.builder()
                .id(1L)
                .title("Learn React")
                .description("Learn React in 2 weeks")
                .completed(false)
                .build();

        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        TodoResponse response = todoService.createTodo(request);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Learn React", response.getTitle());
        assertEquals("Learn React in 2 weeks", response.getDescription());
        assertEquals(false, response.getCompleted());
        verify(todoRepository, times(1)).save(any(Todo.class));

    }
    @Test
    void shouldUpdateTodoSuccessfully() {
        TodoRequest request = new TodoRequest();
        request.setTitle("Updated Todo");
        request.setDescription("Updated Description");

        Todo existingTodo = Todo.builder()
                .id(1L)
                .title("Old Todo")
                .description("Old Description")
                .completed(false)
                .build();

        Todo updatedTodo = Todo.builder()
                .id(1L)
                .title("Updated Todo")
                .description("Updated Description")
                .completed(false)
                .build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        TodoResponse result = todoService.updateTodo(1L, request);

        assertEquals("Updated Todo", result.getTitle());
        assertEquals("Updated Description", result.getDescription());

        verify(todoRepository).findById(1L);
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void shouldToggleTodoSuccessfully() {
        Todo todo = Todo.builder()
                .id(1L)
                .title("Learn Java")
                .description("OOP")
                .completed(false)
                .build();

        Todo toggledTodo = Todo.builder()
                .id(1L)
                .title("Learn Java")
                .description("OOP")
                .completed(true)
                .build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(toggledTodo);

        TodoResponse result = todoService.toggleTodo(1L);

        assertTrue(result.getCompleted());

        verify(todoRepository).findById(1L);
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void shouldDeleteTodoSuccessfully() {
        Todo todo = Todo.builder()
                .id(1L)
                .title("Delete Todo")
                .completed(false)
                .build();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.deleteTodo(1L);

        verify(todoRepository).findById(1L);
        verify(todoRepository).delete(todo);
    }

    @Test
    void shouldThrowExceptionWhenTodoNotFound() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> todoService.toggleTodo(99L)
        );

        assertEquals("Todo not found", exception.getMessage());

        verify(todoRepository).findById(99L);
        verify(todoRepository, never()).save(any(Todo.class));
    }
}
