import { useState } from "react";
import API from "../api";

export default function Register({ setIsRegister }) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {
      await API.post("/auth/register", {
        name,
        email,
        password,
      });
      alert("Registration successful! Please login.");
      setIsRegister(false);
    // eslint-disable-next-line no-unused-vars
    } catch (err) {
      alert("Registration failed");
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h2>Create Account</h2>

        <input
          placeholder="Full Name"
          onChange={(e) => setName(e.target.value)}
        />

        <input
          placeholder="Email"
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleRegister}>Register</button>

        <div className="auth-switch">
          Already have an account?{" "}
          <span onClick={() => setIsRegister(false)}>Login</span>
        </div>
      </div>
    </div>
  );
}