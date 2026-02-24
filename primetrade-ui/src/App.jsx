import { useState } from "react";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";

function App() {
  const [auth, setAuth] = useState(!!localStorage.getItem("token"));
  const [isRegister, setIsRegister] = useState(false);

  if (auth) {
    return <Dashboard setAuth={setAuth} />;
  }

  return isRegister ? (
    <Register setIsRegister={setIsRegister} />
  ) : (
    <Login setAuth={setAuth} setIsRegister={setIsRegister} />
  );
}

export default App;