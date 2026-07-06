import {useEffect, useState} from "react";  
import TodoItem from "../components/TodoItem";
import type {Todo} from "../types/todo";
import {getTodos} from "../api/todoApi";
import '../pages/TodoPage.css';
function TodoPage() {
    const [todos, setTodos] = useState<Todo[]>([]);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [keyword, setKeyword] = useState('');
    const [status, setStatus] = useState("all");
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const size=5;

    const fetchTodos = async () => {
        try {
            const data = await getTodos({
                keyword,
                status,
                page,
                size,
                sortBy: 'createdAt',
                direction: 'desc'
            });
            
            setTodos(data.content ?? []);
            setTotalPages(data.totalPages);
        }catch (error) {
            console.error('Error fetching todos:', error);
        }
    };

    useEffect(() => {
        fetchTodos();
    }, [page, status]);

    return (
        <div className="app">
      <div className="container">
        <h1>Todo List</h1>

         <div className="todo-list">
          {todos.length === 0 ? (
            <p className="empty">No todos found</p>
          ) : (
            todos.map((todo) => (
              <TodoItem
                key={todo.id}
                todo={todo}
                />
            ))
          )}
        </div>
        </div>
      </div>
    )
}
export default TodoPage;