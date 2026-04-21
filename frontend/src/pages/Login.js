import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  // Store username
  const [username, setUsername] = useState("");

  // Store password
  const [password, setPassword] = useState("");

  // Function to call backend API*****
  const handleLogin = async () => {

    try {
      //  Call Spring Boot API
      const API = process.env.REACT_APP_API_URL;
      const response = await fetch(`${API}/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          username: username,
          password: password
        })
      });

      //  Convert response to JSON
      const data = await response.json();

      console.log("API Response:", data);

      // Check success
      if (data.success) {

        // Store token in browser
      localStorage.setItem("token", data.data.token);
      localStorage.setItem("role", data.data.role); //store role after login
      localStorage.setItem("refreshToken", data.data.refreshToken);
        
        alert("Login successful ✅");
        // redirect to dashboard
        navigate("/dashboard");

      } else {
        alert(data.message);
      }

    } catch (error) {
      console.error("Error:", error);
      alert("Something went wrong");
    }
  };

  return (
    <div style={{ textAlign: "center", marginTop: "100px" }}>

      <h2>Login Page</h2>

      {/* Username */}
      <input
        type="text"
        placeholder="Enter Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <br /><br />

      {/* Password */}
      <input
        type="password"
        placeholder="Enter Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <br /><br />

      {/* Login Button */}
      <button onClick={handleLogin}>
        Login
      </button>

    </div>
  );
}

export default Login;