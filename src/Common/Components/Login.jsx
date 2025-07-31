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


  const handleChange = (e) => {
    const { name, value } = e.target;
    setDto((prev) => ({ ...prev, [name]: value }));
  };

  let loginResponse;
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
      const roles = decoded.roles;
      role = roles[0];
      console.log(role);

      const redirectMap = {
        ADMIN: '/admin/home',
        USER: '/user/home',
        SUPER_ADMIN: '/super-admin/home'
      };
      const path = redirectMap[role] ?? '/';

      navigate(path, { replace: true });

    } catch (error) {
      if (err.response) {
    console.error('Login failed:', err.response.status, err.response.data);
  } else if (err.request) {
    console.error('No response received:', err.request);
  } else {
    console.error('Request setup error:', err.message);
  }
  alert("Login failed");
  navigate("/");
    }
  };

  return (
    // <div className="container-fluid d-flex justify-content-center align-items-center min-vh-100 p-3 bg-light">
    //   <div className="card shadow w-100" style={{ maxWidth: '500px' }}>
    //     <div className="card-body">
    //       <h3 className="text-center mb-4">Login</h3>
    //       <form onSubmit={handleSubmit}>
    //         <div className="mb-3">
    //           <label>Username</label>
    //           <input
    //             type="text"
    //             name="username"
    //             className="form-control"
    //             placeholder="Enter Username"
    //             value={dto.username}
    //             onChange={handleChange}
    //             required
    //           />
    //         </div>
    //         <div className="mb-3">
    //           <label>Password</label>
    //           <input
    //             type="password"
    //             name="password"
    //             className="form-control"
    //             placeholder="Enter Password"
    //             value={dto.password}
    //             onChange={handleChange}
    //             required
    //           />
    //         </div>
    //         <div className="d-grid">
    //           <button type="submit" className="btn btn-success">
    //             Login
    //           </button>
    //         </div>
    //       </form>
    //     </div>
    //   </div>
    // </div>

    <>
    <div className="d-flex justify-content-center align-items-center bg-body-tertiary px-3" style={{ minHeight: '88vh' }}>
        <div className="card shadow p-4 rounded-4 w-100" style={{ maxWidth: '400px', backgroundColor: '#ffffff' }}>
          <h3 className="text-center fw-bold text-dark mb-1">Login</h3>
          <p className="text-center text-secondary mb-4">Welcome back! Please login to continue.</p>

          {/* ✅ updated onSubmit handler */}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
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
