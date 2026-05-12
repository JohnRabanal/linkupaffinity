import React from 'react';

export default function AboutUs() {
  return (
    <div className="bg-slate-50 font-sans text-gray-900 min-h-screen">
      <nav className="p-6">
        <a href="/" className="text-blue-600 font-bold flex items-center">
          <i className="fa-solid fa-location-dot mr-2"></i> FindMyRide Cebu
        </a>
      </nav>

      <main className="max-w-4xl mx-auto px-4 py-12">
        <div className="bg-white rounded-2xl border border-gray-100 p-10 md:p-16 shadow-sm">
          <h1 className="text-4xl font-extrabold mb-6 text-center">About Us</h1>
          <div className="space-y-6 text-gray-600 leading-relaxed">
            <p>
              Navigating Cebu City's bustling public transportation system is a daily reality for thousands of Cebuanos. But in the rush of commuting via PUJs (Public Utility Jeepneys) and buses, leaving personal belongings behind happens more often than we think.
            </p>
            <p>
              <strong>FindMyRide Cebu</strong> was born out of a simple idea: to create a centralized, community-driven platform where commuters and drivers can connect to return lost items to their rightful owners. 
            </p>
            <h2 className="text-2xl font-bold text-black mt-8 mb-4">Our Mission</h2>
            <p>
              We aim to foster honesty and community spirit in Cebu. Whether you left your wallet on an 04L jeepney or found a phone on a 13C route, our platform makes it easy to log the item, specify the route, and establish a safe line of communication for recovery.
            </p>
          </div>
        </div>
      </main>
    </div>
  );
}