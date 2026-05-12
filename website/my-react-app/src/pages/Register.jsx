import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function Register() {
  // 1. Create state to hold input values
  const [values, setValues] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  const navigate = useNavigate();

  // 2. Handle input changes
  const handleInput = (event) => {
    setValues(prev => ({ ...prev, [event.target.name]: event.target.value }));
  };

  // 3. Handle Form Submission
  const handleSubmit = async (event) => {
    event.preventDefault();

    // Basic Validation: Check if passwords match
    if (values.password !== values.confirmPassword) {
      return alert("Passwords do not match!");
    }

    try {
      // Send data to your Node.js backend
      const response = await axios.post('http://localhost:3000/user-signup', {
        username: values.username,
        email: values.email,
        password: values.password
      });

      if (response.data.message === 'User account created!') {
        alert("Registration Successful!");
        navigate('/login'); // Redirect to login page
      }
    } catch (err) {
      console.error(err);
      alert("Registration failed. Try again.");
    }
  };

  return (
    <div className="bg-slate-50 min-h-screen">
      <nav className="p-6">
        <a href="/" className="text-blue-600 font-bold flex items-center">
          <i className="fa-solid fa-location-dot mr-2"></i> FindMyRide Cebu
        </a>
      </nav>

      <div className="max-w-md mx-auto bg-white p-10 rounded-2xl border border-gray-100 shadow-sm mb-10">
        <h2 className="text-2xl font-bold mb-1">Create Account</h2>
        <p className="text-gray-400 text-sm mb-8">Join FindMyRide Cebu to report and find lost items</p>
        
        {/* 4. Connect the handleSubmit function */}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-semibold mb-1">Full Name</label>
            <input 
              name="username" 
              type="text" 
              placeholder="Juan Dela Cruz" 
              className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" 
              onChange={handleInput} 
              required 
            />
          </div>
          <div>
            <label className="block text-sm font-semibold mb-1">Email</label>
            <input 
              name="email" 
              type="email" 
              placeholder="juan@example.com" 
              className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" 
              onChange={handleInput} 
              required 
            />
          </div>
          <div>
            <label className="block text-sm font-semibold mb-1">Password</label>
            <input 
              name="password" 
              type="password" 
              placeholder="At least 6 characters" 
              className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" 
              onChange={handleInput} 
              required 
            />
          </div>
          <div>
            <label className="block text-sm font-semibold mb-1">Confirm Password</label>
            <input 
              name="confirmPassword" 
              type="password" 
              placeholder="Confirm your password" 
              className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" 
              onChange={handleInput} 
              required 
            />
          </div>

          <button type="submit" className="w-full bg-black text-white py-3 rounded-xl font-bold hover:bg-gray-800 transition">
            Create Account
          </button>
        </form>
        <p className="text-center text-sm text-gray-500 mt-6">
          Already have an account? <a href="/login" className="text-blue-600 font-semibold">Login</a>
        </p>
      </div>
    </div>
  );
} 