import { useEffect, useState } from "react";
import { listUsers, createUser, updateUserName, deleteUser, type User } from "../lib/api";

export default function Users() {
	const [users, setUsers] = useState<User[]>([]);
	const [name, setName] = useState("");
	const [error, setError] = useState("");

	useEffect(() => {
		(async () => {
			try {
				setUsers(await listUsers());
			} catch (e: any) {
				setError(e?.message ?? "Failed to load users");
			}
		})();
	}, []);

	async function handleAdd() {
		if (!name.trim()) return;
		try {
			const u = await createUser({ name });
			setUsers(prev => [u, ...prev]);
			setName("");
		} catch (e: any) { setError(e?.message ?? "Failed to create user"); }
	}

	async function handleRename(id: number) {
		const newName = prompt("New name?");
		if (!newName) return;
		try {
			const u = await updateUserName(id, newName);
			setUsers(prev => prev.map(x => x.id === id ? u : x));
		} catch (e: any) { setError(e?.message ?? "Failed to update user"); }
	}

	async function handleDelete(id: number) {
		if (!confirm("Delete user?")) return;
		try {
			await deleteUser(id);
			setUsers(prev => prev.filter(x => x.id !== id));
		} catch (e: any) { setError(e?.message ?? "Failed to delete user"); }
	}

	return (
		<main>
			<h1>Users</h1>
			<nav><a href="/">Home</a> · <a href="/tasks">Tasks</a></nav>
			<div>
				<label htmlFor="name">Name</label><br />
				<input id="name" aria-label="Name" value={name} onChange={e => setName(e.target.value)} />
				<button onClick={handleAdd}>Add</button>
			</div>
			{error && <p role="alert">Error: {error}</p>}
			<ul>
				{users.map(u => (
					<li key={u.id}>
						<strong>{u.name}</strong> (#{u.id})	
						<button onClick={() => handleRename(u.id)}>Rename</button>	
						<button onClick={() => handleDelete(u.id)}>Delete</button>
					</li>
				))}
			</ul>
		</main>
	);
}
