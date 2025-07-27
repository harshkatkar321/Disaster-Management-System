import React, { useState } from 'react';
import { loginRequest } from '../services/LoginService';
import { jwtDecode } from "jwt-decode";
import { useNavigate } from 'react-router-dom';

export const Login = () => {
  const navigate = useNavigate();
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
      console.error('Login error:', error);
    }
  };

  return (
    <div className="container-fluid d-flex justify-content-center align-items-center min-vh-100 p-3 bg-light">
      <div className="card shadow w-100" style={{ maxWidth: '500px' }}>
        <div className="card-body">
          <h3 className="text-center mb-4">Login</h3>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label>Username</label>
              <input
                type="text"
                name="username"
                className="form-control"
                placeholder="Enter Username"
                value={dto.username}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label>Password</label>
              <input
                type="password"
                name="password"
                className="form-control"
                placeholder="Enter Password"
                value={dto.password}
                onChange={handleChange}
                required
              />
            </div>
            <div className="d-grid">
              <button type="submit" className="btn btn-success">
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
