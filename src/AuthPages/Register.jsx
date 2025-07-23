import React from 'react';
import { NavbarStatic } from '../components/NavbarStatic';

const Register = () => {
  return (
    <>
    <NavbarStatic/>
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-4 shadow" style={{ width: '100%', maxWidth: '500px' }}>
        <h3 className="text-center mb-4">Register</h3>
        <form>
          <div className="mb-3">
            <label>First Name</label>
            <input type="text" className="form-control" placeholder="First Name" required />
          </div>
          <div className="mb-3">
            <label>Last Name</label>
            <input type="text" className="form-control" placeholder="Last Name" required />
          </div>
          <div className="mb-3">
            <label>Email</label>
            <input type="email" className="form-control" placeholder="Email" required />
          </div>
          <div className="mb-3">
            <label>Password</label>
            <input type="password" className="form-control" placeholder="Password" required />
          </div>
          <div className="mb-3">
            <label>Location</label>
            <input type="text" className="form-control" placeholder="Location" required />
          </div>
          <div className="mb-3">
            <label>Contact Number</label>
            <input type="tel" className="form-control" placeholder="Contact no." required />
          </div>
          <div className="mb-3">
            <label>Profile Picture</label>
            <input type="file" className="form-control" accept="image/*" />
          </div>
          <div className="d-grid">
            <button type="submit" className="btn btn-success">Register</button>
          </div>
        </form>
      </div>
    </div>
    </>
  );
};

export default Register;
