import React, { useState } from 'react';
import { AlertsByLocation } from './services/AlertListByLocation';

export const AlertByLocation = () => {
  const [location, setLocation] = useState('');
  const [alerts, setAlerts] = useState([]);
  const [message, setMessage] = useState('');

  const handleSearch = async () => {
    if (!location.trim()) {
      setMessage("⚠️ Please enter a location.");
      setAlerts([]);
      return;
    }

    try {
      const response = await AlertsByLocation(location);
      if (response && response.length > 0) {
        setAlerts(response);
        setMessage('');
      } else {
        setAlerts([]);
        setMessage(`ℹ️ No alerts found for location: "${location}"`);
      }
    } catch (error) {
      console.error("Error fetching alerts:", error);
      setAlerts([]);
      setMessage("❌ Error fetching alerts. Please try again.");
    }
  };

  return (
    <div  className="max-w-2xl mx-auto p-6 bg-white shadow-lg rounded-xl mt-10">
      <h2 className="text-2xl font-bold text-gray-800 mb-4 text-center">
        🌍 Search Alerts by Location
      </h2>

      <div className="flex flex-col sm:flex-row gap-2 mb-4">
        <input
          type="text"
          placeholder="Enter location..."
          className="flex-1 border border-gray-300 rounded-md px-4 py-2 text-sm focus:ring-2 focus:ring-blue-500"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        />
        <button
          onClick={handleSearch}
          className="bg-blue-600 text-white px-5 py-2 rounded-md hover:bg-blue-700 transition duration-200"
        >
          🔍 Search
        </button>
      </div>

      {message && (
        <div className="mb-4 text-center text-sm text-red-600 font-medium">
          {message}
        </div>
      )}

      {alerts.length > 0 && (
        <div className="grid gap-4">
          {alerts.map((alert, index) => (
            <div
              key={index}
              className="p-4 border-l-4 border-red-500 bg-red-50 rounded-md shadow"
            >
              <h3 className="text-lg font-semibold text-red-700">{alert.title}</h3>
              <p className="text-gray-700 text-sm">{alert.description}</p>
              <div className="text-xs text-gray-500 mt-1">
                📍 {alert.location} | 🕒 {alert.updatedAt}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
