import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function ManageUser() {
  // Updated to point to your Node.js backend
  const API = "http://localhost:3000"; 

  const [users, setUsers] = useState([]);
  const [form, setForm] = useState({
    user_id: "",
    username: "",
    email: "", // Added email field to match database
    password: "",
    role: "",
  });
  const [isEdit, setIsEdit] = useState(false);
  const navigate = useNavigate();

  const isAdmin = localStorage.getItem("userRole") === "Admin";

  // 1. Fetch Users from Node backend
  const fetchUsers = async () => {
    try {
      const res = await axios.get(`${API}/get-users`);
      setUsers(Array.isArray(res.data) ? res.data : []);
    } catch (error) {
      console.error(error);
      alert("Failed to load users from rabanal_db.");
    }
  };

  useEffect(() => {
    if (isAdmin) {
      fetchUsers();
    }
  }, [isAdmin]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const resetForm = () => {
    setForm({ user_id: "", username: "", email: "", password: "", role: "" });
    setIsEdit(false);
  };

  // 2. Add or Update User
  const handleSubmit = async (e) => {
    e.preventDefault();

    const url = isEdit ? `${API}/update-user` : `${API}/user-signup`;

    try {
      const res = await axios.post(url, form);
      alert(res.data.message);
      resetForm();
      fetchUsers();
    } catch (error) {
      console.error(error);
      alert("Operation failed. Check backend console.");
    }
  };

  // 3. Delete User
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this user?")) return;

    try {
      const res = await axios.post(`${API}/delete-user`, { id });
      alert(res.data.message);
      fetchUsers();
    } catch (error) {
      console.error(error);
      alert("Delete failed.");
    }
  };

  const handleEdit = (user) => {
    setForm({
      user_id: user.id, // Match the 'id' column from MySQL
      username: user.username,
      email: user.email,
      password: "",
      role: user.role,
    });
    setIsEdit(true);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  if (!isAdmin) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-100">
        <div className="bg-white p-10 rounded-2xl shadow-lg text-center">
          <h1 className="text-3xl font-bold text-red-600 mb-4">Access Denied</h1>
          <p className="text-gray-600">Only administrators can access this page.</p>
          <button onClick={() => navigate("/login")} className="inline-block mt-6 bg-blue-700 text-white px-6 py-3 rounded-xl font-semibold">
            Go to Login
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-100">
      {/* Navbar */}
      <nav className="bg-white border-b border-gray-100 px-8 py-4 flex justify-between items-center">
        <a href="/manage-users" className="flex items-center text-blue-600 font-bold text-xl">
          <i className="fa-solid fa-location-dot mr-2"></i> FindMyRide Cebu
        </a>
        <button onClick={handleLogout} className="bg-red-500 text-white px-4 py-2 rounded-lg font-semibold hover:bg-red-600 transition">
          Logout
        </button>
      </nav>

      {/* Header */}
      <div className="bg-blue-700 text-white px-10 py-8 shadow">
        <h1 className="text-3xl font-bold">Manage Users</h1>
        <p className="text-blue-100 mt-2">Add, update, and remove commuter or driver accounts in Cebu.</p>
      </div>

      <main className="max-w-6xl mx-auto px-6 py-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-5 mb-8">
          <div className="bg-white p-5 rounded-2xl shadow">
            <p className="text-gray-500 text-sm">Total Users</p>
            <h2 className="text-3xl font-bold text-blue-700">{users.length}</h2>
          </div>
          <div className="bg-white p-5 rounded-2xl shadow">
            <p className="text-gray-500 text-sm">Commuters</p>
            <h2 className="text-3xl font-bold text-green-600">{users.filter(u => u.role === "Commuter").length}</h2>
          </div>
          <div className="bg-white p-5 rounded-2xl shadow">
            <p className="text-gray-500 text-sm">Drivers</p>
            <h2 className="text-3xl font-bold text-yellow-600">{users.filter(u => u.role === "Driver").length}</h2>
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Form */}
          <section className="bg-white p-6 rounded-2xl shadow">
            <h2 className="text-2xl font-bold mb-4">{isEdit ? "Update User" : "Add User"}</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <input type="text" name="username" value={form.username} onChange={handleChange} placeholder="Full Name" className="w-full border rounded-xl p-3 outline-none focus:ring-2 focus:ring-blue-500" required />
              <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="Email" className="w-full border rounded-xl p-3 outline-none focus:ring-2 focus:ring-blue-500" required />
              <input type="password" name="password" value={form.password} onChange={handleChange} placeholder={isEdit ? "New Password (Leave blank to keep)" : "Password"} className="w-full border rounded-xl p-3 outline-none focus:ring-2 focus:ring-blue-500" required={!isEdit} />
              <select name="role" value={form.role} onChange={handleChange} className="w-full border rounded-xl p-3 outline-none focus:ring-2 focus:ring-blue-500" required>
                <option value="">Select Role</option>
                <option value="Commuter">Commuter</option>
                <option value="Driver">Driver</option>
                <option value="Admin">Admin</option>
              </select>
              <button type="submit" className={`w-full py-3 rounded-xl text-white font-bold ${isEdit ? "bg-yellow-500" : "bg-blue-700"}`}>
                {isEdit ? "Update User" : "Add User"}
              </button>
              {isEdit && <button type="button" onClick={resetForm} className="w-full py-3 rounded-xl bg-gray-200">Cancel</button>}
            </form>
          </section>

          {/* User Table */}
          <section className="lg:col-span-2 bg-white p-6 rounded-2xl shadow">
            <h2 className="text-2xl font-bold mb-4">System Users</h2>
            <div className="overflow-x-auto rounded-xl border">
              <table className="w-full">
                <thead className="bg-slate-800 text-white">
                  <tr>
                    <th className="p-4 text-left">Name</th>
                    <th className="p-4 text-left">Role</th>
                    <th className="p-4 text-center">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((user) => (
                    <tr key={user.id} className="border-b">
                      <td className="p-4">
                        <div className="font-bold">{user.username}</div>
                        <div className="text-xs text-gray-400">{user.email}</div>
                      </td>
                      <td className="p-4">
                        <span className="bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-xs font-bold">{user.role}</span>
                      </td>
                      <td className="p-4 text-center space-x-2">
                        <button onClick={() => handleEdit(user)} className="text-yellow-600 font-bold text-sm">Edit</button>
                        <button onClick={() => handleDelete(user.id)} className="text-red-600 font-bold text-sm">Delete</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </section>
        </div>
      </main> 
    </div>
  );
}