import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <BrowserRouter>
     <ToastContainer position="top-right" autoClose={2000} />
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={ <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;