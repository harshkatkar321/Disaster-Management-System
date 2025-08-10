import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ChangePassword, ForgotPasswordWithOtp } from '../services/LoginService';


export const ForgotPassword = () => {
  const navigate = useNavigate();

  // ✅ Single form state
  const [formData, setFormData] = useState({
    email: '',
  });
  const [otp,setOtp] = useState({
    email:'',
    otp:'',
    newPassword:''
  })

  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }),
  ),
  setOtp(prev => ({
      ...prev,
      [name]: value,
    }))
  };

   const handleOtpChange = (e) => {
    const { name, value } = e.target;
    setOtp(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
 
      const res = await ForgotPasswordWithOtp(formData); 
      
      alert('✅ OTP has been sent to your email.');
      setShowOtpInput(true);
    } catch (err) {
      setError('Something went wrong. Please try again.');
    }
  };

  const handleOtpSubmit = async (e) => {
    e.preventDefault();
    try{
      console.log(otp);
      console.log(formData);
      await ChangePassword(otp);
      alert("Password changed successfully");
    }
    catch(err){
      alert("Password change failed");
    }
    
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '500px' }}>
      <h3 className="mb-4 text-center">🔐 Forgot Password</h3>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      {/* Email Form */}
      {!showOtpInput && (
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label>Email Address</label>
            <input
              type="email"
              className="form-control"
              name="email"
              required
              value={formData.email}
              onChange={handleChange}
            />
          </div>

          <button type="submit" className="btn btn-primary w-100">
            Send OTP
          </button>
        </form>
      )}

      {/* OTP Form */}
      {showOtpInput && !otpVerified && (
        <form onSubmit={handleOtpSubmit} className="mt-4">
          <label htmlFor="otp" className="form-label">Enter OTP</label>
          <input
            type="text"
            id="otp"
            name="otp"
            className="form-control"
            placeholder="Enter OTP"
            value={otp.otp}
            onChange={handleOtpChange}
          />
          <label htmlFor="otp" className="form-label">Enter New Password</label>
          <input
            type="text"
            id="newPassword"
            name="newPassword"
            className="form-control"
            placeholder="Enter New Password"
            value={otp.newPassword}
            onChange={handleOtpChange}
          />
          <button type="submit" className="btn btn-success w-100 mt-3">
            Submit OTP
          </button>
        </form>
      )}

      
    </div>
  );
};
