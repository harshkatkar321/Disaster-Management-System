import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { verifyOtpRequest } from '../services/LoginService'; // You'll define this

const UserOtpPage = () => {
  const [otp, setOtp] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const location = useLocation();

  const email = location.state?.email; // Passed from register page

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await verifyOtpRequest(email, otp);
      alert('OTP verified successfully');
      navigate('/login');
    } catch (err) {
      setError('Invalid OTP. Please try again.');
    }
  };

  return (
    <div className="container py-5">
      <h3 className="text-center mb-4">Enter the OTP sent to your email</h3>
      <form onSubmit={handleSubmit} className="col-md-6 mx-auto">
        <input
          type="number"
          className="form-control mb-3"
          placeholder="Enter OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
          required
        />
        {error && <div className="text-danger mb-2">{error}</div>}
        <button className="btn btn-primary w-100" type="submit">
          Verify OTP
        </button>
      </form>
    </div>
  );
};

export default UserOtpPage;
