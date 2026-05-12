import React from 'react';

export default function ContactUs() {
  return (
    <div className="bg-slate-50 min-h-screen">
      <nav className="p-6">
        <a href="/" className="text-blue-600 font-bold flex items-center">
          <i className="fa-solid fa-location-dot mr-2"></i> FindMyRide Cebu
        </a>
      </nav>

      <div className="max-w-xl mx-auto bg-white p-10 rounded-2xl border border-gray-100 shadow-sm mt-8 mb-10">
        <h2 className="text-3xl font-bold mb-2">Contact Support</h2>
        <p className="text-gray-400 text-sm mb-8">Having trouble with the app? Send us a message.</p>
        
        <form className="space-y-5">
          <div>
            <label className="block text-sm font-semibold mb-1">Name</label>
            <input type="text" placeholder="Your Name" className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" required />
          </div>
          <div>
            <label className="block text-sm font-semibold mb-1">Email</label>
            <input type="email" placeholder="your@email.com" className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500" required />
          </div>
          <div>
            <label className="block text-sm font-semibold mb-1">Message</label>
            <textarea rows="4" placeholder="How can we help you?" className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg outline-none focus:ring-2 focus:ring-blue-500 resize-none" required></textarea>
          </div>
          <button type="submit" className="w-full bg-black text-white py-3 rounded-xl font-bold hover:bg-gray-800 transition">
            Send Message
          </button>
        </form>
      </div>
    </div>
  );
}