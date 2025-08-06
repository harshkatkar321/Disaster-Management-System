import React, { useState } from 'react';
import { registerRequest } from '../services/LoginService';
import { useNavigate, Link } from 'react-router-dom';
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

  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();
  const [imageFile, setImageFile] = useState(null);
  const [validationErrors, setValidationErrors] = useState({});
  const [imagePreview, setImagePreview] = useState(null);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setImageFile(file);

    if (file) {
      const reader = new FileReader();
      reader.onload = () => setImagePreview(reader.result);
      reader.readAsDataURL(file);
    } else {
      setImagePreview(null);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setValidationErrors({});

    try {
      await registerRequest(formData, imageFile);
      alert('Registration Successful');
      navigate('/otp');
    } catch (err) {
      if (err.response?.data?.errors) {
        const errors = {};
        err.response.data.errors.forEach((error) => {
          const [field, message] = error.split(':').map((s) => s.trim());
          errors[field] = message;
        });
        setValidationErrors(errors);
      } else {
        alert('Registration failed. Please try again.');
      }
    }
  };

  return (
    <div
      className="d-flex flex-column min-vh-100"
      style={{
        background: 'linear-gradient(135deg, #e9f0ff 0%, #f9fbff 100%)',
      }}
    >
      {/* <Navbar /> */}

      <main className="flex-grow-1 d-flex align-items-center py-5">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-md-8 col-lg-6">
              <div className="card shadow rounded-4 border-0">
                <div className="card-header bg-primary text-white py-4 rounded-top-4 text-center">
                  <h3 className="mb-2 fw-bold">
                    <i className="bi bi-person-plus me-2"></i>Create Your Account
                  </h3>
                  <div
                    style={{
                      height: '4px',
                      width: '80px',
                      backgroundColor: '#fff',
                      margin: '0 auto',
                      borderRadius: '2px',
                    }}
                  ></div>
                </div>

                <div className="card-body p-4 p-md-5">
                  <form onSubmit={handleRegister} noValidate>
                    <div className="row g-3">
                      {/* First Name */}
                      <div className="col-md-6">
                        <label htmlFor="firstName" className="form-label fw-semibold">
                          First Name <span className="text-danger">*</span>
                        </label>
                        <div className="input-group has-validation">
                          <span className="input-group-text bg-light">
                            <i className="bi bi-person text-primary"></i>
                          </span>
                          <input
                            id="firstName"
                            type="text"
                            className={`form-control ${
                              validationErrors.firstName ? 'is-invalid' : ''
                            }`}
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            placeholder="First Name"
                            required
                          />
                          <div className="invalid-feedback">{validationErrors.firstName}</div>
                        </div>
                      </div>

                      {/* Last Name */}
                      <div className="col-md-6">
                        <label htmlFor="lastName" className="form-label fw-semibold">
                          Last Name <span className="text-danger">*</span>
                        </label>
                        <div className="input-group has-validation">
                          <span className="input-group-text bg-light">
                            <i className="bi bi-person text-primary"></i>
                          </span>
                          <input
                            id="lastName"
                            type="text"
                            className={`form-control ${
                              validationErrors.lastName ? 'is-invalid' : ''
                            }`}
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleChange}
                            placeholder="Last Name"
                            required
                          />
                          <div className="invalid-feedback">{validationErrors.lastName}</div>
                        </div>
                      </div>

                      {/* Email */}
                      <div className="col-12">
                        <label htmlFor="email" className="form-label fw-semibold">
                          Email <span className="text-danger">*</span>
                        </label>
                        <div className="input-group has-validation">
                          <span className="input-group-text bg-light">
                            <i className="bi bi-envelope text-primary"></i>
                          </span>
                          <input
                            id="email"
                            type="email"
                            className={`form-control ${validationErrors.email ? 'is-invalid' : ''}`}
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            placeholder="Email"
                            required
                          />
                          <div className="invalid-feedback">{validationErrors.email}</div>
                        </div>
                      </div>

                      {/* Password */}
                      <div className="col-12">
                        <label htmlFor="password" className="form-label fw-semibold">
                          Password <span className="text-danger">*</span>
                        </label>
                        <div className="input-group has-validation">
                          <span className="input-group-text bg-light">
                            <i className="bi bi-lock text-primary"></i>
                          </span>
                          <input
                            id="password"
                            type={showPassword ? 'text' : 'password'}
                            className={`form-control ${
                              validationErrors.password ? 'is-invalid' : ''
                            }`}
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            placeholder="Password"
                            required
                          />
                          <button
                            className="btn btn-outline-secondary"
                            type="button"
                            onClick={togglePasswordVisibility}
                            aria-label={showPassword ? 'Hide password' : 'Show password'}
                          >
                            <i className={`bi ${showPassword ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                          </button>
                          <div className="invalid-feedback">{validationErrors.password}</div>
                        </div>
                      </div>

                      {/* Phone Number */}
                      <div className="col-md-6">
                        <label htmlFor="phoneNumber" className="form-label fw-semibold">
                          Phone Number <span className="text-danger">*</span>
                        </label>
                        <div className="input-group has-validation">
                          <span className="input-group-text bg-light">
                            <i className="bi bi-telephone text-primary"></i>
                          </span>
                          <input
                            id="phoneNumber"
                            type="tel"
                            className={`form-control ${
                              validationErrors.phoneNumber ? 'is-invalid' : ''
                            }`}
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            placeholder="Phone Number"
                            required
                          />
                          <div className="invalid-feedback">{validationErrors.phoneNumber}</div>
                        </div>
                      </div>

                      {/* City */}
                      <div className="col-md-6">
                        <label htmlFor="city" className="form-label fw-semibold">
                          City <span className="text-danger">*</span>
                        </label>
                        <div className="input-group has-validation">
                          <span className="input-group-text bg-light">
                            <i className="bi bi-geo-alt text-primary"></i>
                          </span>
                          <input
                            id="city"
                            type="text"
                            className={`form-control ${validationErrors.city ? 'is-invalid' : ''}`}
                            name="city"
                            value={formData.city}
                            onChange={handleChange}
                            placeholder="City"
                            required
                          />
                          <div className="invalid-feedback">{validationErrors.city}</div>
                        </div>
                      </div>

                      {/* Profile Picture - Optional */}
                      <div className="col-12">
                        <label htmlFor="profilePicture" className="form-label fw-semibold">
                          Profile Picture (Optional)
                        </label>
                        <input
                          id="profilePicture"
                          type="file"
                          className="form-control"
                          onChange={handleFileChange}
                          accept="image/*"
                        />
                        <div className="form-text mb-3">
                          Upload a profile picture (JPEG, PNG under 2MB)
                        </div>
                        {imagePreview && (
                          <div className="text-center">
                            <img
                              src={imagePreview}
                              alt="Profile preview"
                              className="rounded-circle shadow-sm"
                              style={{ width: '120px', height: '120px', objectFit: 'cover' }}
                            />
                          </div>
                        )}
                      </div>

                      {/* Submit Button */}
                      <div className="col-12 mt-4">
                        <button type="submit" className="btn btn-primary w-100 py-2 fw-bold fs-5">
                          <i className="bi bi-check-circle me-2"></i> Register
                        </button>
                      </div>

                      {/* Login Link */}
                      <div className="col-12 text-center mt-3">
                        <p className="mb-0">
                          Already have an account?{' '}
                          <Link
                            to="/login"
                            className="text-decoration-none fw-semibold text-primary"
                          >
                            Sign In
                          </Link>
                        </p>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>

      {/* <Footer /> */}
    </div>
  );
};

export default Register;
