import React, { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import { UserProfile } from './services/UserProfile';

export const UserProfilePage = () => {
  const [profile, setProfile] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      alert("Session expired. Please login again.");
      navigate('/login');
      return;
    }

    try {
      const decoded = jwtDecode(token);
      const username = decoded.sub;

      const fetchProfile = async () => {
        const data = await UserProfile(username);
        console.log("Fetched data:", data);

        if (data) {
          setProfile(data);

          // ✅ Convert image byte array to object URL
          
        } else {
          alert("User not found or session expired.");
        }
      };

      fetchProfile();
    } catch (err) {
      console.error("Token decoding error:", err);
      alert("Invalid session. Please login.");
      navigate('/login');
    }

    // ✅ Clean up object URL to prevent memory leaks
    
  }, [navigate]);

  if (!profile) {
    return <div className="text-center mt-5">Loading user profile...</div>;
  }

  const {
    firstName,
    lastName,
    phoneNumber,
    city,
    username,
    profilePicture,
    imageData,
    imageType
  } = profile;

  const base64Image = imageData ? `data:${imageType};base64,${imageData}` : null;

  return (
    <div className="container mt-5">
      <div className="card shadow border-primary-subtle rounded-4" style={{ maxWidth: '700px', margin: '0 auto' }}>
        <div className="card-header bg-primary text-white fw-bold fs-4">
          👤 User Profile
        </div>
        <div className="card-body d-flex flex-column flex-md-row align-items-center p-4">
          
          {/* ✅ UPDATED IMAGE BLOCK */}
          
            <img
              src={base64Image}
              alt="Profile"
              className="rounded-circle mb-3 mb-md-0 me-md-4"
              style={{ width: '150px', height: '150px', objectFit: 'cover', border: '2px solid #ccc' }}
            />
          

          <div className="w-100">
            <p><strong>First Name:</strong> {firstName}</p>
            <p><strong>Last Name:</strong> {lastName}</p>
            <p><strong>Username:</strong> {username}</p>
            <p><strong>Phone Number:</strong> {phoneNumber}</p>
            <p><strong>City:</strong> {city}</p>
           
          </div>
        </div>
        <div className="card-footer text-center">
          <button className="btn btn-outline-primary" onClick={() => navigate('/user/home')}>
            <i className="bi bi-house-door me-2"></i>Back to Home
          </button>
          <button className="btn btn-outline-primary" onClick={() => navigate('/user/update')}>
            <i className="bi bi-house-door me-2"></i>Update
          </button>
        </div>
      </div>
    </div>
  );
};
