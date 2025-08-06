import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AdminRegisterRequest from '../Admin/services/AdminRegisterService';
import {
  FaUserShield, FaUser, FaEnvelope, FaLock, FaPhone,
  FaCity, FaUpload, FaCheck, FaEye, FaEyeSlash, FaTimes
} from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AdminRegister = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    phoneNumber: '',
    email: '',
    password: '',
    city: '',
  });

  const [imageFile, setImageFile] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const validateForm = () => {
    if (!formData.firstName.trim() || !formData.lastName.trim()) {
      toast.error('First and last names are required');
      return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      toast.error('Please enter a valid email address');
      return false;
    }

    if (formData.password.length < 8) {
      toast.error('Password must be at least 8 characters long');
      return false;
    }

    const phoneRegex = /^[0-9]{10,15}$/;
    if (!phoneRegex.test(formData.phoneNumber)) {
      toast.error('Phone number must be 10-15 digits');
      return false;
    }

    if (!formData.city.trim()) {
      toast.error('City is required');
      return false;
    }

    return true;
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;
    setIsSubmitting(true);

    try {
      await AdminRegisterRequest(formData, imageFile);

      toast.success('Admin created successfully!', {
        position: "top-center",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
      });

      setTimeout(() => {
        navigate('/super-admin/home', { state: { registrationSuccess: true } });
      }, 3000);

    } catch (err) {
      console.error('Registration failed:', err);
      const errorMessage =
        err.response?.data?.message || err.message || 'Registration failed. Please try again.';
      toast.error(errorMessage, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="min-vh-100 d-flex align-items-center bg-light">
      <ToastContainer />
      <div className="container py-5">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <div className="card border-0 shadow-lg rounded-3 overflow-hidden">
              <div className="card-header bg-primary text-white py-4">
                <div className="d-flex align-items-center justify-content-center">
                  <FaUserShield className="fs-1 me-3" />
                  <div>
                    <h1 className="h3 mb-0">Create Admin User</h1>
                    <p className="mb-0 opacity-75">Register a new administrator account</p>
                  </div>
                </div>
              </div>

              <div className="card-body p-5">
                <form onSubmit={handleRegister}>
                  <div className="row g-3">
                    {/* First Name */}
                    <div className="col-md-6">
                      <div className="form-floating">
                        <input
                          type="text"
                          className="form-control"
                          id="firstName"
                          name="firstName"
                          placeholder="First Name"
                          value={formData.firstName}
                          onChange={handleChange}
                          required
                        />
                        <label htmlFor="firstName" className="d-flex align-items-center">
                          <FaUser className="me-2" /> First Name <span className="text-danger ms-1">*</span>
                        </label>
                      </div>
                    </div>

                    {/* Last Name */}
                    <div className="col-md-6">
                      <div className="form-floating">
                        <input
                          type="text"
                          className="form-control"
                          id="lastName"
                          name="lastName"
                          placeholder="Last Name"
                          value={formData.lastName}
                          onChange={handleChange}
                          required
                        />
                        <label htmlFor="lastName" className="d-flex align-items-center">
                          <FaUser className="me-2" /> Last Name <span className="text-danger ms-1">*</span>
                        </label>
                      </div>
                    </div>

                    {/* Email */}
                    <div className="col-12">
                      <div className="form-floating">
                        <input
                          type="email"
                          className="form-control"
                          id="email"
                          name="email"
                          placeholder="Email"
                          value={formData.email}
                          onChange={handleChange}
                          required
                        />
                        <label htmlFor="email" className="d-flex align-items-center">
                          <FaEnvelope className="me-2" /> Email Address <span className="text-danger ms-1">*</span>
                        </label>
                      </div>
                    </div>

                    {/* Password */}
                    <div className="col-12">
                      <div className="form-floating position-relative">
                        <input
                          type={showPassword ? "text" : "password"}
                          className="form-control pe-5"
                          id="password"
                          name="password"
                          placeholder="Password"
                          value={formData.password}
                          onChange={handleChange}
                          required
                          minLength="8"
                        />
                        <label htmlFor="password" className="d-flex align-items-center">
                          <FaLock className="me-2" /> Password <span className="text-danger ms-1">*</span>
                        </label>
                        <button
                          type="button"
                          className="btn btn-link position-absolute end-0 top-0 mt-3 me-2 text-secondary"
                          onClick={togglePasswordVisibility}
                        >
                          {showPassword ? <FaEyeSlash /> : <FaEye />}
                        </button>
                      </div>
                      <small className="text-muted">Minimum 8 characters</small>
                    </div>

                    {/* Phone */}
                    <div className="col-12">
                      <div className="form-floating">
                        <input
                          type="tel"
                          className="form-control"
                          id="phoneNumber"
                          name="phoneNumber"
                          placeholder="Phone Number"
                          value={formData.phoneNumber}
                          onChange={handleChange}
                          required
                          pattern="[0-9]{10,15}"
                        />
                        <label htmlFor="phoneNumber" className="d-flex align-items-center">
                          <FaPhone className="me-2" /> Contact Number <span className="text-danger ms-1">*</span>
                        </label>
                      </div>
                      <small className="text-muted">10-15 digits only</small>
                    </div>

                    {/* City */}
                    <div className="col-12">
                      <div className="form-floating">
                        <input
                          type="text"
                          className="form-control"
                          id="city"
                          name="city"
                          placeholder="City"
                          value={formData.city}
                          onChange={handleChange}
                          required
                        />
                        <label htmlFor="city" className="d-flex align-items-center">
                          <FaCity className="me-2" /> City <span className="text-danger ms-1">*</span>
                        </label>
                      </div>
                    </div>

                    {/* Image */}
                    <div className="col-12">
                      <label htmlFor="profileImage" className="form-label d-flex align-items-center">
                        <FaUpload className="me-2" /> Profile Picture
                      </label>
                      <input
                        type="file"
                        className="form-control"
                        id="profileImage"
                        onChange={handleFileChange}
                        accept="image/*"
                      />
                      <div className="form-text">Optional - Upload a profile picture</div>
                    </div>

                    {/* Submit */}
                    <div className="col-12 mt-4">
                      <div className="d-flex justify-content-between align-items-center">
                        <small className="text-muted">
                          <span className="text-danger">*</span> Required fields
                        </small>
                        <button
                          type="submit"
                          className="btn btn-primary btn-lg px-4 py-3"
                          disabled={isSubmitting}
                        >
                          {isSubmitting ? (
                            <>
                              <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                              Creating Account...
                            </>
                          ) : (
                            <>
                              <FaCheck className="me-2" /> Create Admin Account
                            </>
                          )}
                        </button>
                      </div>
                    </div>
                  </div>
                </form>

                {/* Back Button */}
                <div className="text-center mt-4">
                  <button
                    type="button"
                    className="btn btn-outline-secondary"
                    onClick={() => navigate('/super-admin/home')}
                  >
                    <FaTimes className="me-2" /> Back to Super Admin Page
                  </button>
                </div>

              </div> {/* End card-body */}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminRegister;
