import { useState } from "react";
import API from "../api";

export default function Login({ setAuth, setIsRegister }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const res = await API.post("/auth/login", { email, password });
      localStorage.setItem("token", res.data.token);
      setAuth(true);
    // eslint-disable-next-line no-unused-vars
    } catch (err) {
      alert("Invalid credentials");
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h2>PrimeTrade Login</h2>

        <input
          placeholder="Email"
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin}>Login</button>

        <div className="auth-switch">
          Don’t have an account?{" "}
          <span onClick={() => setIsRegister(true)}>Register</span>
        </div>
      </div>
    </div>
  );
}