import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {

  //  Get token from localStorage
  const token = localStorage.getItem("token");

  //  If no token → redirect to login
  if (!token) {
    return <Navigate to="/" />;
  }

  // If token exists → allow access
  return children;
}

export default ProtectedRoute;