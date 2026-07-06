import type { Todo } from '../types/todo';
import '../components/TodoItem.css';
interface Props {
    todo: Todo;
    onToggle: (id: number) => void;
    onDelete: (id: number) => void;
    onEdit: (todo: Todo) => void;
}


function TodoItem({ todo, onToggle, onDelete, onEdit }: Props) {
  return (
    <div className={`todo-item ${todo.completed ? "completed" : ""}`}>
      <div className="todo-content">
        <h3>{todo.title}</h3>
        <p>{todo.description || "No description"}</p>
        <span>{todo.completed ? "Completed" : "Pending"}</span>
      </div>
      <div className="todo-actions">
        <button onClick={() => onToggle(todo.id)}>
          {todo.completed ? "Undo" : "Done"}
        </button>
        <button onClick={() => onEdit(todo)}>Edit</button>
        <button className="danger" onClick={() => onDelete(todo.id)}>
          Delete
        </button>
      </div>
    </div>
  );
}
export default TodoItem;

