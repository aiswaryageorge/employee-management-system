import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear(); // clear tokens
    navigate("/"); // go to login
  };

  return (
    <div style={{
      display: "flex",
      justifyContent: "space-between",
      padding: "15px 30px",
    //   backgroundColor: "#282c34",
      color: "white",
      backgroundColor: "#1e1e2f",
      borderBottom: "2px solid #444"
      
    }}>
      <h3>EMS Dashboard</h3>

      <button
  className="btn btn-danger"
  onClick={handleLogout}
>
        Logout
      </button>
    </div>
  );
}

export default Navbar;