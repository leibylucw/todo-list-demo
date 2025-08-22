type Json = unknown;

async function http<T = Json>(path: string, init?: RequestInit): Promise<T> {
	const res = await fetch(path, {
		headers: { Accept: "application/json", ...(init?.headers || {}) },
		...init
	});
	if (!res.ok) {
		const text = await res.text().catch(() => "");
		throw new Error(`HTTP ${res.status} ${res.statusText} – ${text.slice(0,200)}`);
	}
	const ct = res.headers.get("content-type") || "";
	if (res.status === 204 || !ct.includes("application/json")) return undefined as T;
	return res.json() as Promise<T>;
}

/* ===== Users ===== */
export type User = { id: number; name: string };

export const listUsers = () =>
	http<User[]>("/api/users");

export const createUser = (body: { name: string }) =>
	http<User>("/api/users", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(body)
	});

export const updateUserName = (userId: number, name: string) =>
	http<User>(`/api/users/${userId}`, {
		method: "PATCH",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ name })
	});

export const deleteUser = (userId: number) =>
	http<void>(`/api/users/${userId}`, { method: "DELETE" });

/* ===== Tasks ===== */
export type Task = { id: number; name: string; description?: string | null; userId: number };

export const listTasks = (userId?: number) =>
	userId
		? http<Task[]>(`/api/users/${userId}/tasks`)
		: http<Task[]>("/api/tasks");

export const createTask = (body: { name: string; description?: string | null; userId: number }) =>
	http<Task>("/api/tasks", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(body)
	});

export const updateTask = (taskId: number, body: { name?: string; description?: string | null; userId?: number }) =>
	http<Task>(`/api/tasks/${taskId}`, {
		method: "PATCH",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(body)
	});

export const deleteTask = (taskId: number) =>
	http<void>(`/api/tasks/${taskId}`, { method: "DELETE" });
