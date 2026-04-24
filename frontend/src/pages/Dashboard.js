import { useEffect, useState } from "react";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { toast } from "react-toastify";
function Dashboard() {

  //  Store employees list
  console.log("Dashboard loaded");
  const [employees, setEmployees] = useState([]);
  const [name, setName] = useState("");
  const [department, setDepartment] = useState("");
  const [email, setEmail] = useState("")
  const [salary, setSalary] = useState("")
  const role = localStorage.getItem("role");
  const [loading, setLoading] = useState(false);
  // const [toast, setToast] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [sortField, setSortField] = useState("name");
  const [sortDir, setSortDir] = useState("asc");
  // Fetch employees when page loads
  const fetchEmployees = async () => {

      try {
        setLoading(true); // 🔥 start loading
        //  Call backend API
        // const response = await fetch("http://localhost:8080/employees", {
        //   method: "GET",
        //   headers: {
        //     "Authorization": "Bearer " + token
        //   }
        // });
        // const response = await api.get(`/employees/page?page=${page}&size=5&search=${searchTerm}`);
        const response = await api.get(`/employees/page?page=${page}&size=5&search=${searchTerm}&sort=${sortField},${sortDir}`);
        // const data = await response.json();
        console.log("Employees:" , response.data);
        setEmployees(response.data.content || []);
        setTotalPages(response.data.totalPages || 0);

      } catch (error) {
        console.error(error);
         setEmployees([]);
  } finally {
    setLoading(false); // 🔥 stop loading
  }
    };
  useEffect(() => {
    fetchEmployees();

  }, [toast , page, searchTerm, sortField, sortDir]); // runs once
  //  Logout function
const handleLogout = async () => {

     const token = localStorage.getItem("token");

  try {
    await fetch(`${process.env.REACT_APP_API_URL}/auth/logout`, {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + token
      }
    });
  } catch (error) {
    console.error("Logout error:", error);
  }

  // Remove all auth data
  localStorage.removeItem("token");
  localStorage.removeItem("role");

  // Redirect to login
  window.location.href = "/";
  //navigate("/");
};

const handleAddEmployee = async () => {
  if (!name || !department || !email || !salary) {
    toast.warning("Please fill all fields ⚠️");
    return;
  }
  try {
     setLoading(true);
   const newEmployee = {
   name,
   email,
   department,
   salary
};

    const response = await api.post("/employees", newEmployee);
    toast.success("Employee added successfully ✅");

    // refresh list after adding
    fetchEmployees();
     setName("");
    setDepartment("");
     setEmail("");
    setSalary("");

  } catch (error) {
    toast.error("Failed to add employee ❌");
  } finally {
    setLoading(false);
  }
};
const handleDelete = async (id) => {
  const confirmDelete = window.confirm("Are you sure you want to delete?");

  if (!confirmDelete) return;

  try {
     setLoading(true);
    await api.delete(`/employees/${id}`);
     toast.success("Employee deleted 🗑️");
    // refresh list
    fetchEmployees();

  } catch (error) {
    toast.error("Delete failed ❌");
  } finally {
    setLoading(false);
  }
};
const handleEdit = (emp) => {
  setName(emp.name);
  setDepartment(emp.department);
  setEmail(emp.email);
  setSalary(emp.salary);
  setEditingId(emp.id);
};
const handleUpdateEmployee = async () => {

  if (!name || !department || !email || !salary) {
    toast.success("Please fill all fields ⚠️");
    return;
  }

  try {
    setLoading(true);

    await api.put(`/employees/${editingId}`, {
     name,
     email,
     department,
    salary
    });

    toast.success("Employee updated ✏️");

    setName("");
    setDepartment("");
    setEmail("");
    setSalary("");
    setEditingId(null);

    fetchEmployees();

  } catch (error) {
    toast.error("Update failed ❌");
  } finally {
    setLoading(false);
  }
};
// const filteredEmployees = employees.filter((emp) =>
//   emp.name.toLowerCase().includes(searchTerm.toLowerCase())
// );
const pageNumbers = [...Array(totalPages).keys()];
 return (
  <>
    <Navbar />

    <div className="container">

      {/* 🔹 TITLE */}
      <h2 className="header">Employee Management</h2>

      {/* 🔍 SEARCH */}
      <input
        className="input"
        type="text"
        placeholder="Search employee..."
        value={searchTerm}
        onChange={(e) => {
          setSearchTerm(e.target.value);
          setPage(0);
        }}
      />

      {/* ➕ ADD / EDIT FORM → ADMIN ONLY */}
      {role === "ROLE_ADMIN" && (
        <div className="form">

          <input
            className="input"
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <input
            className="input"
            type="text"
            placeholder="Department"
            value={department}
            onChange={(e) => setDepartment(e.target.value)}
          />

          <input
            className="input"
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            className="input"
            type="number"
            placeholder="Salary"
            value={salary}
            onChange={(e) => setSalary(e.target.value)}
          />

          <button
            className="btn btn-primary"
            onClick={editingId ? handleUpdateEmployee : handleAddEmployee}
            disabled={!name || !department}
          >
            {editingId ? "Update" : "+ Add"}
          </button>

        </div>
      )}

      {/* 🔽 SORT */}
      <div className="sort-bar">
        <select
          value={sortField}
          onChange={(e) => {
            setSortField(e.target.value);
            setPage(0);
          }}
        >
          <option value="name">Name</option>
          <option value="salary">Salary</option>
        </select>

        <button
          onClick={() => {
            setSortDir(sortDir === "asc" ? "desc" : "asc");
            setPage(0);
          }}
        >
          {sortDir === "asc" ? "↑ Asc" : "↓ Desc"}
        </button>
      </div>

      {/* 📋 EMPLOYEE LIST */}
      {loading ? (
        <p className="loader">Loading employees...</p>
      ) : (
        <>
          {(employees || []).length === 0 ? (
            <p className="empty">No employees found</p>
          ) : (
            employees.map((emp) => (
              <div key={emp.id} className="card">

                <div className="employee-info">
                  <p className="name">{emp.name}</p>
                  <p className="dept">{emp.department}</p>
                  <p className="email">{emp.email}</p>
                  <p className="salary">₹{emp.salary}</p>
                </div>

                {role === "ROLE_ADMIN" && (
                  <div className="actions">
                    <button
                      className="btn btn-primary"
                      onClick={() => handleEdit(emp)}
                    >
                      Edit
                    </button>

                    <button
                      className="btn btn-danger"
                      onClick={() => handleDelete(emp.id)}
                    >
                      Delete
                    </button>
                  </div>
                )}

              </div>
            ))
          )}

          {/* 📄 PAGINATION */}
          <div className="pagination">
            <button
              disabled={page === 0}
              onClick={() => setPage(page - 1)}
            >
              Prev
            </button>

            {pageNumbers.map((num) => (
              <button
                key={num}
                className={page === num ? "active-page" : ""}
                onClick={() => setPage(num)}
              >
                {num + 1}
              </button>
            ))}

            <button
              disabled={page === totalPages - 1}
              onClick={() => setPage(page + 1)}
            >
              Next
            </button>
          </div>
        </>
      )}

    </div>
  </>
);
}

export default Dashboard;
