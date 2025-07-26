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

      const decoded = jwtDecode(response.token);
      const roles = decoded.roles;

      role = roles[0];

      console.log(role);

      // Define mapping
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
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="card border-warning text-center col-12 col-md-6 col-lg-4">
        <div className="card-header border-warning bg-warning">
          <h3>Login Page</h3>
        </div>
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="username"
              className="form-control border-warning"
              placeholder="Enter Username"
              value={dto.username}
              onChange={handleChange}
            />
            <input
              type="password"
              name="password"
              className="form-control border-warning mt-2"
              placeholder="Enter Password"
              value={dto.password}
              onChange={handleChange}
            />
            <div className="mt-3">
              <button type="submit" className="btn btn-lg btn-primary ms-2">
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
