import axios from 'axios';
import type { Todo, TodoPage } from '../types/todo';

const API_BASE_URL = 'http://localhost:8080/api/todos';
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
    const res = await axios.get<TodoPage>(API_BASE_URL, { params: query });
    return res.data;
};

