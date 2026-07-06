import axios from 'axios';
import type { Todo, TodoPage } from '../types/todo';

const API_URL = import.meta.env.VITE_API_BASE_URL;;
export interface TodoRequest {
    title: string;
    description: string;
}

export interface TodoQuery{
    keyword?: string;
    status?: string;
    page?: number;
    size?: number;
    sortBy?: string;
    direction?: string;
}

export const getTodos = async (query: TodoQuery): Promise<TodoPage> => {
    const res = await axios.get<TodoPage>(API_URL, { params: query });
    return res.data;
};
export const createTodo = async (data: TodoRequest): Promise<Todo> => {
  const res = await axios.post<Todo>(API_URL, data);
  return res.data;
};

export const updateTodo = async (
  id: number,
  data: TodoRequest
): Promise<Todo> => {
  const res = await axios.put<Todo>(`${API_URL}/${id}`, data);
  return res.data;
};

export const toggleTodo = async (id: number): Promise<Todo> => {
  const res = await axios.patch<Todo>(`${API_URL}/${id}/toggle`);
  return res.data;
};

export const deleteTodo = async (id: number): Promise<void> => {
  await axios.delete(`${API_URL}/${id}`);
};

