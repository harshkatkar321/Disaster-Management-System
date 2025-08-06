import React, { useState } from 'react';
import { AlertsByLocation } from './services/AlertListByLocation';

export const AlertByLocation = () => {
  const [location, setLocation] = useState('');
  const [alerts, setAlerts] = useState([]);
  const [message, setMessage] = useState('');

  const handleSearch = async () => {
    const trimmed = location.trim();
    if (!trimmed) {
      setMessage('⚠️ Please enter a location.');
      setAlerts([]);
      return;
    }

    try {
      const response = await AlertsByLocation(trimmed);
      if (response?.length > 0) {
        setAlerts(response);
        setMessage('');
      } else {
        setAlerts([]);
        setMessage(`ℹ️ No alerts found for "${trimmed}"`);
      }
    } catch (error) {
      console.error('Error fetching alerts:', error);
      setAlerts([]);
      setMessage('❌ Failed to fetch alerts. Please try again later.');
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 py-10">
      <div className="bg-white shadow-md rounded-xl p-6">
        <h2 className="text-3xl font-extrabold text-gray-800 mb-6 text-center">
          🌍 Search Alerts by Location
        </h2>

        <div className="flex flex-col sm:flex-row gap-3 mb-5">
          <input
            type="text"
            placeholder="Enter a location (e.g., Mumbai, Delhi)..."
            className="flex-1 border border-gray-300 rounded-md px-4 py-2 text-base focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />
          <button
            onClick={handleSearch}
            className="bg-blue-600 text-white px-6 py-2 rounded-md text-base font-medium hover:bg-blue-700 transition duration-200"
          >
            🔍 Search
          </button>
        </div>

        {message && (
          <div className="text-center text-sm text-red-600 font-medium mb-4 animate-pulse">
            {message}
          </div>
        )}

        {alerts.length > 0 && (
          <div className="space-y-4 animate-fadeIn">
            {alerts.map((alert, index) => (
              <div
                key={index}
                className="bg-red-50 border-l-4 border-red-600 p-4 rounded-md shadow-sm hover:shadow-md transition"
              >
                <div className="flex items-center justify-between mb-1">
                  <h3 className="text-lg font-bold text-red-700">{alert.title}</h3>
                  <span className="text-xs bg-red-100 text-red-700 px-2 py-0.5 rounded-full">
                    📍 {alert.location}
                  </span>
                </div>
                <p className="text-gray-700 text-sm">{alert.description}</p>
                <div className="text-xs text-gray-500 mt-2">
                  🕒 Last Updated: {new Date(alert.updatedAt).toLocaleString()}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
