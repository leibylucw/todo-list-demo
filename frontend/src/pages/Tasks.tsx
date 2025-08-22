import { useEffect, useState } from "react";
import { listTasks, createTask, updateTask, deleteTask, listUsers, type Task, type User } from "../lib/api";

export default function Tasks() {
	const [tasks, setTasks] = useState<Task[]>([]);
	const [users, setUsers] = useState<User[]>([]);
	const [name, setName] = useState("");
	const [description, setDescription] = useState("");
	const [userId, setUserId] = useState<number | "">("");
	const [error, setError] = useState("");

	useEffect(() => {
		(async () => {
			try {
				const [t, u] = await Promise.all([listTasks(), listUsers()]);
				setTasks(t); setUsers(u);
			} catch (e: any) { setError(e?.message ?? "Failed to load"); }
		})();
	}, []);

	async function handleAdd() {
		if (!name.trim() || !userId) return;
		try {
			const t = await createTask({ name, description: description || undefined, userId: Number(userId) });
			setTasks(prev => [t, ...prev]);
			setName(""); setDescription(""); setUserId("");
		} catch (e: any) { setError(e?.message ?? "Failed to create task"); }
	}

	async function handleReassign(id: number) {
		const choice = prompt("Reassign to userId?");
		if (!choice) return;
		try {
			const t = await updateTask(id, { userId: Number(choice) });
			setTasks(prev => prev.map(x => x.id === id ? t : x));
		} catch (e: any) { setError(e?.message ?? "Failed to update task"); }
	}

	async function handleDelete(id: number) {
		if (!confirm("Delete task?")) return;
		try {
			await deleteTask(id);
			setTasks(prev => prev.filter(x => x.id !== id));
		} catch (e: any) { setError(e?.message ?? "Failed to delete task"); }
	}

	return (
		<main>
			<h1>Tasks</h1>
			<nav><a href="/">Home</a> · <a href="/users">Users</a></nav>

			<fieldset>
				<legend>Create Task</legend>
				<label htmlFor="tname">Name</label><br />
				<input id="tname" value={name} aria-label="Task name" onChange={e => setName(e.target.value)} /><br />
				<label htmlFor="tdesc">Description (optional)</label><br />
				<input id="tdesc" value={description} aria-label="Task description" onChange={e => setDescription(e.target.value)} /><br />
				<label htmlFor="tuser">User</label><br />
				<select id="tuser" aria-label="Owner" value={userId} onChange={e => setUserId(e.target.value ? Number(e.target.value) : "")}>
					<option value="">Select user</option>
					{users.map(u => <option key={u.id} value={u.id}>{u.name} (#{u.id})</option>)}
				</select><br />
				<button onClick={handleAdd}>Add</button>
			</fieldset>

			{error && <p role="alert">Error: {error}</p>}
			<ul>
				{tasks.map(t => (
					<li key={t.id}>
						<strong>{t.name}</strong> — {t.description ?? "(no description)"} · owner #{t.userId}
						<button onClick={() => handleReassign(t.id)}>Reassign</button>
						<button onClick={() => handleDelete(t.id)}>Delete</button>
					</li>
				))}
			</ul>
		</main>
	);
}
