import { useEffect, useState } from "react";
import TodoItem from "../components/TodoItem";
import type { Todo } from "../types/todo";
import {
  createTodo,
  deleteTodo,
  getTodos,
  toggleTodo,
  updateTodo,
} from "../api/todoApi";
import "../pages/TodoPage.css";
function TodoPage() {
  const [todos, setTodos] = useState<Todo[]>([]);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const [keyword, setKeyword] = useState("");
  const [status, setStatus] = useState("all");

  const [editingTodo, setEditingTodo] = useState<Todo | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const size = 5;

  const fetchTodos = async () => {
    try {
      const data = await getTodos({
        keyword,
        status,
        page,
        size,
        sortBy: "createdAt",
        direction: "desc",
      });

      setTodos(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      alert("Cannot load todos");
    }
  };

  useEffect(() => {
    fetchTodos();
  }, [page, status]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!title.trim()) {
      alert("Title is required");
      return;
    }

    try {
      if (editingTodo) {
        await updateTodo(editingTodo.id, {
          title,
          description,
        });
      } else {
        await createTodo({
          title,
          description,
        });
      }

      setTitle("");
      setDescription("");
      setEditingTodo(null);
      setIsModalOpen(false);
      setPage(0);
      await fetchTodos();
    } catch (error) {
      alert("Save failed");
    }
  };

  const handleOpenCreate = () => {
    setEditingTodo(null);
    setTitle("");
    setDescription("");
    setIsModalOpen(true);
  };

  const handleEdit = (todo: Todo) => {
    setEditingTodo(todo);
    setTitle(todo.title);
    setDescription(todo.description || "");
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setEditingTodo(null);
    setTitle("");
    setDescription("");
    setIsModalOpen(false);
  };

  const handleToggle = async (id: number) => {
    await toggleTodo(id);
    await fetchTodos();
  };

  const handleDelete = async (id: number) => {
    const confirmed = confirm("Delete this todo?");
    if (!confirmed) return;

    await deleteTodo(id);
    await fetchTodos();
  };

  const handleSearch = () => {
    setPage(0);
    fetchTodos();
  };

  return (
    <div className="app">
      <div className="container">
        <div className="page-header">
          <h1>Todo List</h1>
          <button type="button" className="add-button" onClick={handleOpenCreate}>
            Add Todo
          </button>
        </div>

        <div className="filters">
          <input
            type="text"
            placeholder="Search..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />

          <select value={status} onChange={(e) => setStatus(e.target.value)}>
            <option value="all">All</option>
            <option value="completed">Completed</option>
            <option value="pending">Pending</option>
          </select>

          <button type="button" onClick={handleSearch}>
            Search
          </button>
        </div>

        <div className="todo-list">
          {todos.length === 0 ? (
            <p className="empty">No todos found</p>
          ) : (
            todos.map((todo) => (
              <TodoItem
                key={todo.id}
                todo={todo}
                onToggle={handleToggle}
                onDelete={handleDelete}
                onEdit={handleEdit}
              />
            ))
          )}
        </div>

        <div className="pagination">
          <button disabled={page === 0} onClick={() => setPage(page - 1)}>
            Previous
          </button>

          <span>
            Page {page + 1} / {totalPages || 1}
          </span>

          <button
            disabled={page + 1 >= totalPages}
            onClick={() => setPage(page + 1)}
          >
            Next
          </button>
        </div>
      </div>

      {isModalOpen && (
        <div className="modal-backdrop" onClick={handleCloseModal}>
          <div
            className="todo-modal"
            role="dialog"
            aria-modal="true"
            aria-labelledby="todo-modal-title"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="modal-header">
              <h2 id="todo-modal-title">
                {editingTodo ? "Edit Todo" : "Add Todo"}
              </h2>
              <button
                type="button"
                className="close-button"
                aria-label="Close modal"
                onClick={handleCloseModal}
              >
                x
              </button>
            </div>

            <form className="todo-form" onSubmit={handleSubmit}>
              <input
                type="text"
                placeholder="Todo title"
                value={title}
                autoFocus
                onChange={(e) => setTitle(e.target.value)}
              />

              <textarea
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />

              <div className="form-actions">
                <button type="submit">
                  {editingTodo ? "Update Todo" : "Add Todo"}
                </button>
                <button
                  type="button"
                  className="secondary"
                  onClick={handleCloseModal}
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default TodoPage;
