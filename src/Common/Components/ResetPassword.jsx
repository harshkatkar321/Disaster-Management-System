// src/Common/Components/ResetPassword.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const ResetPassword = () => {
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();

  const handlePasswordReset = (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    if (newPassword !== confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    // ✅ When backend is ready, make API call here
    // await axios.post('/api/auth/reset-password', { newPassword });

    setMessage('✅ Your password has been successfully reset (mock).');
    setNewPassword('');
    setConfirmPassword('');

    setTimeout(() => {
      navigate('/login');
    }, 1500);
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '500px' }}>
      <h3 className="mb-4 text-center">🔒 Reset Your Password</h3>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={handlePasswordReset}>
        <div className="mb-3">
          <label>New Password</label>
          <div className="input-group">
            <input
              type={showNewPassword ? 'text' : 'password'}
              className="form-control"
              required
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
            />
            <button
              type="button"
              className="btn btn-outline-secondary"
              onClick={() => setShowNewPassword((prev) => !prev)}
              tabIndex={-1}
            >
              {showNewPassword ? '🚫' : '👁️'}
            </button>
          </div>
        </div>

        <div className="mb-3">
          <label>Confirm Password</label>
          <div className="input-group">
            <input
              type={showConfirmPassword ? 'text' : 'password'}
              className="form-control"
              required
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <button
              type="button"
              className="btn btn-outline-secondary"
              onClick={() => setShowConfirmPassword((prev) => !prev)}
              tabIndex={-1}
            >
              {showConfirmPassword ? '🚫' : '👁️'}
            </button>
          </div>
        </div>

        <button type="submit" className="btn btn-primary w-100">
          Reset Password
        </button>
      </form>
    </div>
  );
};
