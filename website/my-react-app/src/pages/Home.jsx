import React from "react";

export default function Home() {
  const isAdmin = localStorage.getItem("isAdmin") === "true";

  const handleLogout = () => {
    localStorage.removeItem("isAdmin");
    localStorage.removeItem("userRole");
    window.location.href = "/";
  };

  return (
    <div className="bg-slate-50 min-h-screen flex flex-col">
      {/* Navbar */}
      <nav className="bg-white border-b border-gray-100 px-8 py-4 flex justify-between items-center">
        
        {/* Logo */}
        <div className="flex items-center text-blue-600 font-bold text-xl">
          <i className="fa-solid fa-location-dot mr-2"></i>
          FindMyRide Cebu
        </div>

        {/* Right Side */}
        <div className="flex items-center space-x-6 text-sm">

          {/* User Role */}
          <span className="text-gray-500 italic">
            {isAdmin ? "User (admin)" : "User (commuter)"}
          </span>

          {/* Admin Panel Button */}
          {isAdmin && (
            <a
              href="/manage-users"
              className="bg-blue-700 text-white px-4 py-2 rounded-lg font-semibold hover:bg-blue-800 transition"
            >
              Admin Panel
            </a>
          )}

          {/* Logout */}
          <button
            onClick={handleLogout}
            className="flex items-center space-x-1 font-semibold hover:text-red-600 transition"
          >
            <i className="fa-solid fa-arrow-right-from-bracket"></i>
            <span>Logout</span>
          </button>

        </div>
      </nav>

      {/* Main Content */}
      <main className="max-w-6xl mx-auto p-8 flex-grow w-full">
        <h1 className="text-4xl font-bold mb-2">
          Welcome, Ma'am/Sir!
        </h1>

        <p className="text-gray-500 mb-8">
          Manage your lost and found reports
        </p>

        {/* Action Buttons */}
        <div className="grid md:grid-cols-3 gap-6 mb-12">

          {/* Lost */}
          <button className="bg-white p-6 rounded-2xl border border-gray-100 flex items-center space-x-4 shadow-sm hover:shadow-md transition text-left w-full group">
            <div className="w-12 h-12 bg-red-50 text-red-500 rounded-full flex items-center justify-center text-xl font-bold group-hover:bg-red-100 transition">
              !
            </div>
            <div>
              <h3 className="font-bold">Report Lost Item</h3>
              <p className="text-xs text-gray-400">Lost something?</p>
            </div>
          </button>

          {/* Found */}
          <button className="bg-white p-6 rounded-2xl border border-gray-100 flex items-center space-x-4 shadow-sm hover:shadow-md transition text-left w-full group">
            <div className="w-12 h-12 bg-green-50 text-green-500 rounded-full flex items-center justify-center text-xl font-bold group-hover:bg-green-100 transition">
              <i className="fa-solid fa-cube"></i>
            </div>
            <div>
              <h3 className="font-bold">Report Found Item</h3>
              <p className="text-xs text-gray-400">Found something?</p>
            </div>
          </button>

          {/* Browse */}
          <button className="bg-white p-6 rounded-2xl border border-gray-100 flex items-center space-x-4 shadow-sm hover:shadow-md transition text-left w-full group">
            <div className="w-12 h-12 bg-blue-50 text-blue-500 rounded-full flex items-center justify-center text-xl font-bold group-hover:bg-blue-100 transition">
              <i className="fa-solid fa-magnifying-glass"></i>
            </div>
            <div>
              <h3 className="font-bold">Browse Items</h3>
              <p className="text-xs text-gray-400">Search all reports</p>
            </div>
          </button>

        </div>

        {/* Reports Section */}
        <div className="bg-white rounded-2xl border border-gray-100 p-8 shadow-sm">
          <h2 className="font-bold mb-1">Your Reports</h2>

          <p className="text-xs text-gray-400 mb-6">
            View and manage your lost and found item reports
          </p>

          {/* Tabs */}
          <div className="w-full bg-gray-50 rounded-lg flex mb-12">
            <button className="w-1/2 py-2 font-semibold text-sm bg-white border border-gray-200 rounded-lg shadow-sm">
              Lost Items (0)
            </button>

            <button className="w-1/2 py-2 font-semibold text-sm text-gray-400 hover:text-gray-600 transition">
              Found Items (0)
            </button>
          </div>

          {/* Empty State */}
          <div className="text-center py-20">
            <div className="w-16 h-16 bg-gray-50 text-gray-300 rounded-full flex items-center justify-center text-2xl mx-auto mb-4 italic">
              !
            </div>

            <p className="text-gray-400 text-sm mb-6">
              You haven't reported any lost items yet
            </p>

            <button className="bg-black text-white px-6 py-2 rounded-lg text-sm font-bold flex items-center mx-auto hover:bg-gray-800 transition">
              <span className="mr-2 text-lg">+</span>
              Report Lost Item
            </button>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="w-full bg-white px-8 py-6 text-center text-sm text-gray-400 border-t border-gray-100 mt-auto">
        <p>© 2026 FindMyRide Cebu. All rights reserved.</p>

        <div className="space-x-4 mt-2">
          <a href="/about" className="hover:text-gray-600 transition">
            About
          </a>

          <span>&bull;</span>

          <a href="/contact" className="hover:text-gray-600 transition">
            Contact Support
          </a>
        </div>
      </footer>
    </div>
  );
}