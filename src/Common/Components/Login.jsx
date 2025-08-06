import React, { useState } from 'react';
import { loginRequest } from '../services/LoginService';
import { jwtDecode } from "jwt-decode";
import { useNavigate, Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loginSuccess } from '../../Auth/authSlice';

export const Login = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [dto, setDto] = useState({ username: '', password: '' });
  const [validationErrors, setValidationErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDto((prev) => ({ ...prev, [name]: value }));
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setValidationErrors({});
    setIsLoading(true);

    try {
      const response = await loginRequest(dto);
      localStorage.setItem('token', response.token);
      dispatch(loginSuccess(response.token));

      const decoded = jwtDecode(response.token);
      const roles = decoded.roles || [];
      const role = roles.length > 0 ? roles[0] : '';

      const redirectMap = {
        ADMIN: '/admin/home',
        USER: '/user/home',
        SUPER_ADMIN: '/super-admin/home'
      };

      const path = redirectMap[role] || '/';
      navigate(path, { replace: true });

    } catch (err) {
      console.error('Login failed:', err);

      if (err.response?.data?.errors) {
        const groupedErrors = {};
        err.response.data.errors.forEach(error => {
          const [field, message] = error.split(":").map(s => s.trim());
          groupedErrors[field] = groupedErrors[field] 
            ? groupedErrors[field] + ` | ${message}` 
            : message;
        });
        setValidationErrors(groupedErrors);
      } else {
        alert("Login failed. Please check your credentials and try again.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100 bg-light">
      <div className="card border-0 shadow-lg rounded-3" style={{ width: '100%', maxWidth: '450px' }}>
        <div className="card-header bg-primary text-white py-3 rounded-top-3">
          <h3 className="mb-0 text-center">
            <i className="bi bi-shield-lock me-2"></i>
            Account Login
          </h3>
        </div>
        
        <div className="card-body p-4 p-md-5">
          <form onSubmit={handleSubmit}>
            {/* Email/Username Field */}
            <div className="mb-4">
              <label htmlFor="username" className="form-label fw-semibold">
                Email Address <span className="text-danger">*</span>
              </label>
              <div className="input-group">
                <span className="input-group-text bg-light">
                  <i className="bi bi-envelope text-primary"></i>
                </span>
                <input
                  type="text"
                  className={`form-control ${validationErrors.username ? 'is-invalid' : ''}`}
                  name="username"
                  placeholder="Enter your email"
                  value={dto.username}
                  onChange={handleChange}
                  required
                />
              </div>
              {validationErrors.username && (
                <div className="invalid-feedback d-block">
                  {validationErrors.username}
                </div>
              )}
            </div>

            {/* Password Field with Toggle */}
            <div className="mb-4">
              <label htmlFor="password" className="form-label fw-semibold">
                Password <span className="text-danger">*</span>
              </label>
              <div className="input-group">
                <span className="input-group-text bg-light">
                  <i className="bi bi-lock text-primary"></i>
                </span>
                <input
                  type={showPassword ? "text" : "password"}
                  className={`form-control ${validationErrors.password ? 'is-invalid' : ''}`}
                  name="password"
                  placeholder="Enter your password"
                  value={dto.password}
                  onChange={handleChange}
                  required
                />
                <button 
                  className="btn btn-outline-secondary" 
                  type="button"
                  onClick={togglePasswordVisibility}
                >
                  <i className={`bi ${showPassword ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                </button>
              </div>
              {validationErrors.password && (
                <div className="invalid-feedback d-block">
                  {validationErrors.password}
                </div>
              )}
            </div>

            {/* Remember Me & Forgot Password */}
            <div className="d-flex justify-content-between mb-4">
              <div className="form-check">
                <input className="form-check-input" type="checkbox" id="rememberMe" />
                <label className="form-check-label" htmlFor="rememberMe">
                  Remember me
                </label>
              </div>
              <Link to="/forgot-password" className="text-decoration-none">
                Forgot password?
              </Link>
            </div>

            {/* Submit Button */}
            <div className="d-grid mb-3">
              <button 
                type="submit" 
                className="btn btn-primary py-2 fw-bold"
                disabled={isLoading}
              >
                {isLoading ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                    Logging in...
                  </>
                ) : (
                  <>
                    <i className="bi bi-box-arrow-in-right me-2"></i>
                    Login
                  </>
                )}
              </button>
            </div>

            {/* Divider */}
            <div className="position-relative text-center my-4">
              <hr />
              <span className="position-absolute top-50 translate-middle-y bg-white px-3 text-muted">
                OR
              </span>
            </div>

            {/* Register Link */}
            <div className="text-center">
              <p className="mb-0">
                Don't have an account?{' '}
                <Link to="/register" className="text-decoration-none fw-semibold">
                  Register here
                </Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};