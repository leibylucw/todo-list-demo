// frontend/vite.config.ts
import { defineConfig, type ProxyOptions } from "vite";
import react from "@vitejs/plugin-react";

const proxy: Record<string, string | ProxyOptions> = {
	"/api": {
		target: "http://backend:8080",
		changeOrigin: true,
		// secure: false, // uncomment if your target is https with self-signed cert
	}
};

export default defineConfig({
	plugins: [react()],
	server: {
		host: true,
		port: 5173,
		proxy
	}
});
