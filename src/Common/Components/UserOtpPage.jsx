import React, { useState,useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { RegenerateOtpRequest, verifyOtpRequest } from '../services/LoginService'; // You'll define this

const UserOtpPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email;
  const [formData, setFormData] = useState({
    email:'',
    otp:'',
  });
  const [error, setError] = useState('');

  useEffect(() => {
    if (email) {
      setFormData(prev => ({
        ...prev,
        email: email,
      }));
    }
  }, [email]);
 

   // Passed from register page
   const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleRegenerateOtp = async (e) => {
    e.preventDefault();
    try {
      console.log(formData);
      await RegenerateOtpRequest(formData);
      // alert('OTP verified successfully');
      navigate('/otp');
    } catch (err) {
      const message = err.response?.data || 'Something went wrong. Please try again.';
      setError(message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log(formData);
      const response=await verifyOtpRequest(formData);
      console.log(response);
      alert('OTP verified successfully');
      navigate('/login');
    } catch (err) {
      const message = err.response?.data || 'Something went wrong. Please try again.';
      setError(message);
    }
  };

  return (
    <div className="container py-5">
      <h3 className="text-center mb-4">Enter the OTP sent to your email</h3>
      <form onSubmit={handleSubmit} className="col-md-6 mx-auto">
        <input
          type="text"
          name='otp'
          className="form-control mb-3"
          placeholder="Enter OTP"
          value={formData.otp}
          onChange={handleChange}
          
        />
        {error && <div className="text-danger mb-2">{error}</div>}
        <button className="btn btn-primary w-100" type="submit">
          Verify OTP
        </button>
      </form>
       <div className="text-center">
        <span>Didn't receive the OTP? </span>
        <button
          type="button"
          className="btn btn-link p-0"
          onClick={handleRegenerateOtp}
        >
          Resend OTP
        </button>
      </div>
    </div>
  );
};

export default UserOtpPage;
