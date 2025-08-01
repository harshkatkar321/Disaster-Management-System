import React, { useState } from 'react';
import { loginRequest } from '../services/LoginService';
import { jwtDecode } from "jwt-decode";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loginSuccess } from '../../Auth/authSlice';
import { Link } from 'react-router-dom';

export const Login = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [dto, setDto] = useState({ username: '', password: '' });
  const [validationErrors, setValidationErrors] = useState({});


  const handleChange = (e) => {
    const { name, value } = e.target;
    setDto((prev) => ({ ...prev, [name]: value }));
  };

  // let loginResponse;
  let role = "";

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Submitting DTO:', dto);

    try {
      const response = await loginRequest(dto);  // WAIT for the promise
      console.log('Response object:', response);
      console.log('Token only:', response.token);

      
      // After successful login:
      localStorage.setItem('token', response.token);
      dispatch(loginSuccess(response.token));

      const decoded = jwtDecode(response.token);
      // const roles = decoded.roles;
      // role = roles[0];
      // console.log(role);

      const redirectMap = {
        ADMIN: '/admin/home',
        USER: '/user/home',
        SUPER_ADMIN: '/super-admin/home'
      };
      const path = redirectMap[role] ?? '/';

      navigate(path, { replace: true });

    }  catch (err) {
  console.error('Login failed:', err);

  if (err.response && err.response.data && Array.isArray(err.response.data.errors)) {
    const groupedErrors = {};

    err.response.data.errors.forEach(error => {
      const [field, message] = error.split(":").map(s => s.trim());

      if (field in groupedErrors) {
        groupedErrors[field] += ` | ${message}`;  // concat if multiple errors for same field
      } else {
        groupedErrors[field] = message;
      }
    });

    setValidationErrors(groupedErrors);
  } else {
    alert("Login failed. Please try again.");
  }
}
  };

  return (
 

    <>
    <div className="d-flex justify-content-center align-items-center bg-body-tertiary px-3" style={{ minHeight: '88vh' }}>
        <div className="card shadow p-4 rounded-4 w-100" style={{ maxWidth: '400px', backgroundColor: '#ffffff' }}>
          <h3 className="text-center fw-bold text-dark mb-1">Login</h3>
          <p className="text-center text-secondary mb-4">Welcome back! Please login to continue.</p>

          {/* ✅ updated onSubmit handler */}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              {validationErrors.username && (
                <>
                <small className="text-danger">{validationErrors.username}</small>
                <br/>
                </>
              )}
              <label htmlFor="email" className="form-label fw-semibold">Email Address</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-envelope-fill text-dark"></i>
                </span>
                <input
                  
                  className="form-control border-start-0"
                   type="text"
                name="username"
                placeholder="Enter Username"
                value={dto.username}
                onChange={handleChange}
                required
                />
              </div>
            </div>

            <div className="mb-3">
              {validationErrors.password && (
                <>
                <small className="text-danger">{validationErrors.password}</small>
                <br/>
                </>
              )}
              <label htmlFor="password" className="form-label fw-semibold">Password</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-lock-fill text-dark"></i>
                </span>
                <input
                 type="password"
                name="password"
                className="form-control border-start-0"
                placeholder="Enter Password"
                value={dto.password}
                onChange={handleChange}
                required
                />
                {/* <span
                  className="input-group-text bg-white"
                  onClick={togglePassword}
                  style={{ cursor: 'pointer' }}
                >
                  <i className={`bi ${showPassword ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                </span> */}
              </div>
            </div>

            <div className="d-grid mt-4">
              <button type="submit" className="btn fw-bold text-white" style={{ backgroundColor: '#1e3d59' }}>
                <i className="bi bi-box-arrow-in-right me-2"></i> Log In
              </button>
            </div>
          </form>

          <div className="text-center mt-3">
            <small className="text-muted">
              Forgot your password?{' '}
              <a href="#" className="text-decoration-none text-dark fw-semibold">Click here</a>
            </small>
          </div>

          <div className="text-center mt-2">
            <small className="text-muted">
              New to the system?{' '}
              <Link to="/register" className="text-decoration-none text-dark fw-semibold">Register</Link>
            </small>
          </div>
        </div>
      </div>

    </>
  );
};
