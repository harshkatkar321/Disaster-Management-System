import { jwtDecode } from 'jwt-decode';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import { UserProfile } from './services/UserProfile';
import { AlertsByLocation } from '../Alerts/services/AlertListByLocation';
import { AlertByLocation } from '../Alerts/AlertByLocation';

export const UserHome = () => {
   const [alerts, setAlerts] = useState([]);
    const [location,setLocation]  = useState('');
   const [loading, setLoading] = useState(true);
   const token=localStorage.getItem('token');
   const decoded = jwtDecode(token);
   const username = decoded.sub;

    useEffect(() => {
        const fetchUserAndAlerts = async () => {
            try{
                const userProfile = await UserProfile(username);
                console.log("User profile response: ", userProfile);
                const userLocation = userProfile.city;
                setLocation(userLocation);

                const alertsList = await AlertsByLocation(userLocation);
                console.log("Alert response",alertsList);
                setAlerts(alertsList);
            }
            catch(err){
                // alert("Listing failed");
                console.error("list error",err);
                
            }
            finally{
                setLoading(false);
            }
        };
        fetchUserAndAlerts();
    },[username]);

    return (
          <div className="p-4">
            <AlertByLocation/>
      <h2 className="text-xl font-bold mb-4">Alerts for {location}</h2>

      {loading ? (
        <p>Loading alerts...</p>
      ) : alerts.length === 0 ? (
        <p className="text-gray-600 italic">No alerts for your location.</p>
      ) : (
        <ul className="space-y-2">
          {alerts.map((alert) => (
            <li key={alert.id} className="border p-2 rounded bg-red-100">
              <strong>{alert.title}</strong>
              <p>{alert.description}</p>
              <small className="text-xs text-gray-500">Posted: {alert.updatedAt}</small>
              <p> Hii changes Down </p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};
