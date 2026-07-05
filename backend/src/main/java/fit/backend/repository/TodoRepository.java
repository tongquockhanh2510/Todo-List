package fit.backend.repository;

import fit.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);
    List<Todo> findByCompletedOrderByCreatedAtDesc(Boolean completed);
    List<Todo> findByTitleContainingIgnoreCaseAndCompletedOrderByCreatedAtDesc(String title, Boolean completed);
    List<Todo> findAllByOrderByCreatedAtDesc();
}
