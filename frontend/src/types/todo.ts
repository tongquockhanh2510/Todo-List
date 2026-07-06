export interface Todo{
    id: number;
    title: string;
    description: string;
    completed: boolean;
    createdAt: string;
    updatedAt: string;
}

export interface TodoPage{
    content: Todo[];
    totalPages: number;
    totalElements: number;
    number: number;
    size: number;
}
