import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [form, setForm] = useState({
    email: "", // Changed from username to email to match your database
    password: "",
    role: "Commuter",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      // 1. Decide which endpoint to hit based on the role
      const endpoint = form.role === "Admin" ? "/admin-login" : "/user-login";
      
      // 2. Make the call to your server.js
      const response = await axios.post(`http://localhost:3000${endpoint}`, {
        email: form.email,
        password: form.password
      });

      // 3. If credentials match
      if (response.status === 200) {
        localStorage.setItem("userRole", form.role);
        localStorage.setItem("isLoggedIn", "true");
        
        alert(`${form.role} login successful!`);
        navigate("/home");
      }
    } catch (error) {
      // 4. This catches the 401 "Wrong credentials" from your backend
      if (error.response && error.response.status === 401) {
        alert("Login failed: Invalid email or password.");
      } else {
        alert("Error: Could not connect to the server.");
      }
    }
  };

  return (
    <div className="bg-slate-50 min-h-screen">
      <nav className="p-6">
        <a href="/" className="text-blue-600 font-bold flex items-center">
          <i className="fa-solid fa-location-dot mr-2"></i> FindMyRide Cebu
        </a>
      </nav>

      <div className="max-w-md mx-auto mt-12 bg-white p-10 rounded-2xl border border-gray-100 shadow-sm">
        <h2 className="text-2xl font-bold mb-1">Welcome Back</h2>
        <p className="text-gray-400 text-sm mb-8">Login to your FindMyRide Cebu account</p>

        <form onSubmit={handleLogin} className="space-y-5">
          {/* Email */}
          <div>
            <label className="block text-sm font-semibold mb-2">Email Address</label>
            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              placeholder="Enter your email"
              className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          {/* Password */}
          <div>
            <label className="block text-sm font-semibold mb-2">Password</label>
            <input
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              placeholder="Enter your password"
              className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          {/* User Roles */}
          <div className="space-y-3">
            <p className="text-sm font-semibold">I am a</p>
            <div className="flex items-center space-x-6">
              <label className="flex items-center space-x-2 text-sm">
                <input type="radio" name="role" value="Commuter" checked={form.role === "Commuter"} onChange={handleChange} className="accent-black" />
                <span>Commuter</span>
              </label>
              <label className="flex items-center space-x-2 text-sm">
                <input type="radio" name="role" value="Driver" checked={form.role === "Driver"} onChange={handleChange} className="accent-black" />
                <span>Driver</span>
              </label>
              <label className="flex items-center space-x-2 text-sm">
                <input type="radio" name="role" value="Admin" checked={form.role === "Admin"} onChange={handleChange} className="accent-black" />
                <span>Admin</span>
              </label>
            </div>
          </div>

          <button type="submit" className="w-full bg-black text-white py-3 rounded-xl font-bold hover:bg-gray-800 transition">
            Login
          </button>
        </form>

        <p className="text-center text-sm text-gray-500 mt-6">
          Don't have an account? <a href="/register" className="text-blue-600 font-semibold">Sign up</a>
        </p>
      </div>
    </div>
  );
} 