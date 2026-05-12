import React from "react";

export default function LandingPage() {
  return (
    <div className="bg-slate-50 font-sans text-gray-900 min-h-screen">
      <nav className="bg-white border-b border-gray-100 px-8 py-4 flex justify-between items-center sticky top-0 z-50">
        
        {/* Logo */}
        <div className="flex items-center space-x-8">
          <div className="flex items-center text-blue-600 font-bold text-xl cursor-pointer">
            <i className="fa-solid fa-location-dot mr-2"></i>
            <span>FindMyRide Cebu</span>
          </div>

          <div className="hidden md:flex space-x-6 text-sm font-medium text-gray-600">
            <a href="/about" className="hover:text-blue-600 transition">
              About Us
            </a>

            <a href="/contact" className="hover:text-blue-600 transition">
              Contact Support
            </a>
          </div>
        </div>

        {/* Login / Logout Buttons */}
        <div className="space-x-4">

          {/* If logged in as admin, remove login button and show logout only */}
          {localStorage.getItem("isAdmin") === "true" ? (
            <button
              onClick={() => {
                localStorage.removeItem("isAdmin");
                window.location.href = "/";
              }}
              className="px-4 py-2 text-sm font-medium bg-red-500 text-white rounded-lg hover:bg-red-600 transition"
            >
              Logout
            </button>
          ) : (
            <>
              <a
                href="/login"
                className="px-4 py-2 text-sm font-medium border border-gray-200 rounded-lg hover:bg-gray-50 transition"
              >
                Login
              </a>

              <a
                href="/register"
                className="px-4 py-2 text-sm font-medium bg-black text-white rounded-lg hover:bg-gray-800 transition"
              >
                Sign Up
              </a>
            </>
          )}

        </div>
      </nav>

      {/* Main Section */}
      <main className="max-w-6xl mx-auto px-4 py-16 text-center">
        <h1 className="text-5xl font-extrabold mb-4">
          Lost Something in a Jeepney?
        </h1>

        <p className="text-gray-500 text-lg mb-10 max-w-2xl mx-auto">
          FindMyRide Cebu helps you recover items lost in public transportation
          across Cebu City
        </p>

        <div className="flex justify-center space-x-4 mb-20">
          <a
            href="/login"
            className="bg-black text-white px-8 py-3 rounded-lg font-semibold hover:bg-gray-800 transition"
          >
            Report Lost Item
          </a>

          <a
            href="/login"
            className="bg-white border border-gray-200 text-gray-700 px-8 py-3 rounded-lg font-semibold hover:bg-gray-50 transition"
          >
            Report Found Item
          </a>
        </div>

        {/* How It Works */}
        <div className="bg-white rounded-2xl border border-gray-100 p-12 shadow-sm">
          <h2 className="text-2xl font-bold mb-12">How It Works</h2>

          <div className="grid md:grid-cols-3 gap-8">

            <div className="p-6 border border-gray-50 rounded-xl">
              <div className="w-12 h-12 bg-blue-50 text-blue-600 rounded-full flex items-center justify-center mx-auto mb-4">
                <i className="fa-solid fa-magnifying-glass"></i>
              </div>

              <h3 className="font-bold mb-2">Report & Search</h3>

              <p className="text-gray-500 text-sm">
                Report your lost or found item with details about the jeepney route.
              </p>
            </div>

            <div className="p-6 border border-gray-50 rounded-xl">
              <div className="w-12 h-12 bg-green-50 text-green-600 rounded-full flex items-center justify-center mx-auto mb-4">
                <i className="fa-solid fa-location-dot"></i>
              </div>

              <h3 className="font-bold mb-2">Match Items</h3>

              <p className="text-gray-500 text-sm">
                Our system matches items based on description and location.
              </p>
            </div>

            <div className="p-6 border border-gray-50 rounded-xl">
              <div className="w-12 h-12 bg-purple-50 text-purple-600 rounded-full flex items-center justify-center mx-auto mb-4">
                <i className="fa-solid fa-phone"></i>
              </div>

              <h3 className="font-bold mb-2">Connect & Recover</h3>

              <p className="text-gray-500 text-sm">
                Contact the finder and arrange for a safe return.
              </p>
            </div>

          </div>
        </div>
      </main>
    </div>
  );
}