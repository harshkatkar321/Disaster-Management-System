import React, { useState } from 'react';
import { registerRequest } from '../services/LoginService';
import { useNavigate } from 'react-router-dom';
import { Navbar } from '../../Navigation/Components/Navbar';
import { Footer } from '../../Navigation/Components/Footer';

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

  const handleRegister = async e => {
    e.preventDefault()

    const dto = formData;

    try {
      await registerRequest(dto, imageFile)
      alert('Registration Successful')
      navigate('/')
    } catch (err) {
      console.error('Registration failed:', err)
      alert('Registration failed. Try Again')
    }
  }



  return (
    <>
   
    <div className="d-flex flex-column" style={{ minHeight: '100vh' }}>

      {/* Main Section */}
      <div
        className="flex-grow-1 d-flex justify-content-center align-items-center bg-light"
        style={{ padding: '20px 0' }}
      >
        <div
          className="card shadow-lg p-4 rounded-4 w-100"
          style={{ maxWidth: '480px', border: '1px solid #ccc' }}
        >
          <h3 className="text-center fw-bold mb-2 text-dark">
            <i className="bi bi-person-plus-fill me-2" style={{ color: '#00b894' }}></i>Register
          </h3>
          <p className="text-center text-muted mb-4">Create your Disaster Management account</p>

          {/* Form */}
          <form onSubmit={handleRegister} >
            {/* First Name */}
            <div className="mb-3">
              <label className="form-label fw-semibold">First Name</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-person-fill" style={{ color: '#00b894' }}></i>
                </span>
                <input
                  type="text"
                  className="form-control border-start-0"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
                placeholder="First Name"
                required
                />
              </div>
            </div>

            {/* Last Name */}
            <div className="mb-3">
              <label className="form-label fw-semibold">Last Name</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-person-fill" style={{ color: '#00b894' }}></i>
                </span>
                <input
                    type="text"
                  className="form-control border-start-0"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
                placeholder="Last Name"
                required
                />
              </div>
            </div>

            {/* Email */}
            <div className="mb-3">
              <label className="form-label fw-semibold">Email</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-envelope-fill" style={{ color: '#00b894' }}></i>
                </span>
                <input
                    type="text"
                  className="form-control border-start-0"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Email"
                required
                />
              </div>
            </div>

            {/* Password */}
            <div className="mb-3">
              <label className="form-label fw-semibold">Password</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-lock-fill" style={{ color: '#00b894' }}></i>
                </span>
                <input
                    type="password"
                  className="form-control border-start-0"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Password"
                required
                />
              </div>
            </div>

            {/* Contact Number */}
            <div className="mb-4">
              <label className="form-label fw-semibold">Contact Number</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-telephone-fill" style={{ color: '#00b894' }}></i>
                </span>
                <input
                   type="text"
                  className="form-control border-start-0"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                placeholder="Contact No"
                required
                />
              </div>
            </div>

            <div className="mb-3">
              <label className="form-label fw-semibold">City</label>
              <div className="input-group">
                <span className="input-group-text bg-white border-end-0">
                  <i className="bi bi-envelope-fill" style={{ color: '#00b894' }}></i>
                </span>
                <input
                    type="text"
                  className="form-control border-start-0"
                name="city"
                value={formData.city}
                onChange={handleChange}
                placeholder="City"
                required
                />
              </div>
            </div>

            <div className="mb-3">
               <label className="form-label fw-semibold" >Profile Picture</label>
               <input
                type="file"
                className="form-control"
                onChange={handleFileChange}
                accept="image/*"
              />
            </div>

            {/* Register Button */}
            <div className="d-grid">
              <button
                type="submit"
                className="btn fw-bold text-white"
                style={{ backgroundColor: '#00b894' }}
              >
                <i className="bi bi-check-circle me-2"></i>Register
              </button>
            </div>

            {/* Redirect Link */}
            <div className="text-center mt-3">
              <small className="text-muted">
                Already a member?{' '}
                <a href="/login" className="text-decoration-none" style={{ color: '#00b894' }}>
                  Login here
                </a>
              </small>
            </div>
          </form>
        </div>
      </div>

      {/* Footer */}
      <Footer/>
    </div>
    </>
  )


};

export default Register;
