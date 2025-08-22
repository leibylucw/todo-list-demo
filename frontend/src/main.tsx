import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider, Link } from "react-router-dom";
import Home from "./pages/Home";
import Users from "./pages/Users";
import Tasks from "./pages/Tasks";

const router = createBrowserRouter([
	{ path: "/", element: <Home /> },
	{ path: "/users", element: <Users /> },
	{ path: "/tasks", element: <Tasks /> }
]);

createRoot(document.getElementById("root")!).render(<RouterProvider router={router} />);
