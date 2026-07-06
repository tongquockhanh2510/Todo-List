import type {Todo} from '../types/todo';
import '../components/TodoItem.css';
interface Props {
    todo: Todo;
   
}

function TodoItem({ todo }: Props) {
    return (
        <div className={`todo-item ${todo.completed ? 'completed' : ''}`}>
            <div className="todo-content">
                <h3>{todo.title}</h3>
                <p>{todo.description}</p>
                <span>{todo.completed ? 'Completed' : 'Pending'}</span>
            </div>

          
        </div>
    );
}
export default TodoItem;

