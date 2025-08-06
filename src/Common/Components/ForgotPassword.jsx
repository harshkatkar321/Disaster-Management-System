import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import { sendResetEmail } from '../../Alerts/services/ForgotPasswordService';
import { ResetPassword } from './ResetPassword'; // ✅ import the component

export const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [otp, setOtp] = useState('');
  const [otpVerified, setOtpVerified] = useState(false); // ✅ controls whether ResetPassword is shown

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
      // MOCK backend response
      const res = { message: `Mock: OTP sent to ${email}` };
      await new Promise((resolve) => setTimeout(resolve, 1000));
      // const res = await sendResetEmail(email);

      setMessage(res.message || 'If this email is registered, a reset link has been sent.');
      alert('✅ OTP has been sent to your email.');
      setShowOtpInput(true);
    } catch (err) {
      setError('Something went wrong. Please try again.');
    }
  };

  const handleOtpSubmit = (e) => {
    e.preventDefault();

    // Normally validate with backend, here we mock it:
    if (otp.trim() !== '') {
      alert('✅ OTP verified.');
      setOtpVerified(true); // ✅ show the password reset form
    } else {
      alert('Please enter the OTP');
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '500px' }}>
      <h3 className="mb-4 text-center">🔐 Forgot Password</h3>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Email Address</label>
          <input
            type="email"
            className="form-control"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <button type="submit" className="btn btn-primary w-100">
          Send Reset Link
        </button>
      </form>

      {showOtpInput && !otpVerified && (
        <form onSubmit={handleOtpSubmit} className="mt-4">
          <label htmlFor="otp" className="form-label">Enter OTP</label>
          <input
            type="text"
            id="otp"
            className="form-control"
            placeholder="Enter OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
          />
          <button type="submit" className="btn btn-success w-100 mt-3">
            Submit OTP
          </button>
        </form>
      )}

      {otpVerified && (
        <div className="mt-5">
          <ResetPassword inline={true} />
        </div>
      )}
    </div>
  );
};
