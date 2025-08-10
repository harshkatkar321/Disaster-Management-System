import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { RegisterReourceRequest } from './services/RegisterReourceService';

export const RegisterResource = () => {
    const [formData, setFormData] = useState({
    type: '',
    kind: '',
    name: '',
    phoneNumber:'',
    email: '',
    password: '',
    city: '',
    capacity:'',
    description:'',
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

  const handleResourceRegister = async (e) => {
    e.preventDefault();
    setValidationErrors({});

    try {
        console.log(formData);
      await RegisterReourceRequest(formData, imageFile);
      alert('Registration Successful');
      navigate(`/otp`,{
    state : {
      email:formData.email,
    }
  });
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
                 <form onSubmit={handleResourceRegister} noValidate>
                  <div className="row g-3">

                    {/* Type */}
                    <div className="col-md-6">
                      <label htmlFor="type" className="form-label fw-semibold">Type <span className="text-danger">*</span></label>
                      <input
                        type="text"
                        id="type"
                        name="type"
                        className={`form-control ${validationErrors.type ? 'is-invalid' : ''}`}
                        value={formData.type}
                        onChange={handleChange}
                        placeholder="e.g. Water Unit, Medical Team"
                        required
                      />
                      <div className="invalid-feedback">{validationErrors.type}</div>
                    </div>

                    {/* Kind */}
                    <div className="col-md-6">
                      <label htmlFor="kind" className="form-label fw-semibold">Kind <span className="text-danger">*</span></label>
                      <select
                        name="kind"
                        className={`form-control ${validationErrors.kind ? 'is-invalid' : ''}`}
                        onChange={handleChange}
                        value={formData.kind}
                        required
                      >
                        <option value="">Select Kind</option>
                        <option value="PERSONNEL">Personnel</option>
                        <option value="TEAM">Team</option>
                        <option value="EQUIPMENT">Equipment</option>
                        <option value="SUPPLY">Supply</option>
                        <option value="FACILITY">Facility</option>
                      </select>
                      <div className="invalid-feedback">{validationErrors.kind}</div>
                    </div>

                    {/* Name */}
                    <div className="col-md-6">
                      <label htmlFor="name" className="form-label fw-semibold">Name <span className="text-danger">*</span></label>
                      <input
                        type="text"
                        id="name"
                        name="name"
                        className={`form-control ${validationErrors.name ? 'is-invalid' : ''}`}
                        value={formData.name}
                        onChange={handleChange}
                        placeholder="Resource name"
                        required
                      />
                      <div className="invalid-feedback">{validationErrors.name}</div>
                    </div>

                    {/* Email */}
                    <div className="col-md-6">
                      <label htmlFor="email" className="form-label fw-semibold">Email <span className="text-danger">*</span></label>
                      <input
                        type="email"
                        id="email"
                        name="email"
                        className={`form-control ${validationErrors.email ? 'is-invalid' : ''}`}
                        value={formData.email}
                        onChange={handleChange}
                        required
                      />
                      <div className="invalid-feedback">{validationErrors.email}</div>
                    </div>

                    {/* Password */}
                    <div className="col-md-6">
                      <label htmlFor="password" className="form-label fw-semibold">Password <span className="text-danger">*</span></label>
                      <div className="input-group">
                        <input
                          type={showPassword ? 'text' : 'password'}
                          id="password"
                          name="password"
                          className={`form-control ${validationErrors.password ? 'is-invalid' : ''}`}
                          value={formData.password}
                          onChange={handleChange}
                          placeholder="Password"
                          required
                        />
                        <button type="button" className="btn btn-outline-secondary" onClick={togglePasswordVisibility}>
                          <i className={`bi ${showPassword ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                        </button>
                        <div className="invalid-feedback">{validationErrors.password}</div>
                      </div>
                    </div>

                    {/* Phone Number */}
                    <div className="col-md-6">
                      <label htmlFor="phoneNumber" className="form-label fw-semibold">Phone Number <span className="text-danger">*</span></label>
                      <input
                        type="tel"
                        id="phoneNumber"
                        name="phoneNumber"
                        className={`form-control ${validationErrors.phoneNumber ? 'is-invalid' : ''}`}
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        required
                      />
                      <div className="invalid-feedback">{validationErrors.phoneNumber}</div>
                    </div>

                    {/* City */}
                    <div className="col-md-6">
                      <label htmlFor="city" className="form-label fw-semibold">City <span className="text-danger">*</span></label>
                      <input
                        type="text"
                        id="city"
                        name="city"
                        className={`form-control ${validationErrors.city ? 'is-invalid' : ''}`}
                        value={formData.city}
                        onChange={handleChange}
                        required
                      />
                      <div className="invalid-feedback">{validationErrors.city}</div>
                    </div>

                    {/* Capacity */}
                    <div className="col-md-6">
                      <label htmlFor="capacity" className="form-label fw-semibold">Capacity</label>
                      <input
                        type="number"
                        id="capacity"
                        name="capacity"
                        className={`form-control ${validationErrors.capacity ? 'is-invalid' : ''}`}
                        value={formData.capacity}
                        onChange={handleChange}
                        placeholder="Numeric estimate (e.g. 5000 liters)"
                      />
                      <div className="invalid-feedback">{validationErrors.capacity}</div>
                    </div>

                    {/* Description */}
                    <div className="col-12">
                      <label htmlFor="description" className="form-label fw-semibold">Description</label>
                      <textarea
                        id="description"
                        name="description"
                        className={`form-control ${validationErrors.description ? 'is-invalid' : ''}`}
                        rows="3"
                        value={formData.description}
                        onChange={handleChange}
                        placeholder="Optional description of resource"
                      ></textarea>
                      <div className="invalid-feedback">{validationErrors.description}</div>
                    </div>

                    {/* Profile Picture */}
                    <div className="col-12">
                      <label htmlFor="profilePicture" className="form-label fw-semibold">Profile Picture (Optional)</label>
                      <input
                        type="file"
                        id="profilePicture"
                        className="form-control"
                        onChange={handleFileChange}
                        accept="image/*"
                      />
                      <div className="form-text mb-3">Upload an image (JPG/PNG)</div>
                      {imagePreview && (
                        <div className="text-center">
                          <img
                            src={imagePreview}
                            alt="Preview"
                            className="rounded-circle shadow-sm"
                            style={{ width: '120px', height: '120px', objectFit: 'cover' }}
                          />
                        </div>
                      )}
                    </div>
                  
                    {/* Submit Button */}
                    <div className="col-12 mt-4">
                      <button type="submit" className="btn btn-primary w-100 py-2 fw-bold fs-5">
                        <i className="bi bi-check-circle me-2"></i> Register Resource
                      </button>
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
    )
}
