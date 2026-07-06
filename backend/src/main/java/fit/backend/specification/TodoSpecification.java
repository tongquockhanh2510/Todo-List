package fit.backend.specification;

import fit.backend.entity.Todo;
import org.springframework.data.jpa.domain.Specification;

public class TodoSpecification {

    public static Specification<Todo> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }

            String pattern = "%" + keyword.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Todo> hasCompleted(Boolean completed) {
        return (root, query, cb) -> {
            if (completed == null) {
                return null;
            }

            return cb.equal(root.get("completed"), completed);
        };
    }
}
