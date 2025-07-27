import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    phoneNumber: '',
    email: '',
    password: '',
    city: '',
  });

  const navigate = useNavigate();
  const [imageFile, setImageFile] = useState(null);

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    const submitData = new FormData();
    Object.keys(formData).forEach((key) => {
      submitData.append(key, formData[key]);
    });

    if (imageFile) {
      submitData.append('profileImage', imageFile);
    }

    try {
      const response = await axios.post(
        'http://localhost:8080/api/v1/register',
        submitData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        }
      );
      alert('Registration Successful');
      navigate('/');
    } catch (error) {
      console.error(error);
      alert('Registration failed. Try Again');
    }
  };

  return (
    <div className="container-fluid d-flex justify-content-center align-items-center min-vh-100 p-3 bg-light">
      <div className="card shadow w-100" style={{ maxWidth: '500px' }}>
        <div className="card-body">
          <h3 className="text-center mb-4">Register</h3>
          <form onSubmit={handleRegister}>
            <div className="mb-3">
              <label>First Name</label>
              <input
                type="text"
                className="form-control"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                placeholder="First Name"
                required
              />
            </div>
            <div className="mb-3">
              <label>Last Name</label>
              <input
                type="text"
                className="form-control"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                placeholder="Last Name"
                required
              />
            </div>
            <div className="mb-3">
              <label>Email</label>
              <input
                type="email"
                className="form-control"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Email"
                required
              />
            </div>
            <div className="mb-3">
              <label>Password</label>
              <input
                type="password"
                className="form-control"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Password"
                required
              />
            </div>
            <div className="mb-3">
              <label>City</label>
              <input
                type="text"
                className="form-control"
                name="city"
                value={formData.city}
                onChange={handleChange}
                placeholder="Location"
                required
              />
            </div>
            <div className="mb-3">
              <label>Contact Number</label>
              <input
                type="text"
                className="form-control"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                placeholder="Contact no."
                required
              />
            </div>
            <div className="mb-3">
              <label>Profile Picture</label>
              <input
                type="file"
                className="form-control"
                onChange={handleFileChange}
                accept="image/*"
              />
            </div>
            <div className="d-grid">
              <button type="submit" className="btn btn-success">
                Register
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Register;
