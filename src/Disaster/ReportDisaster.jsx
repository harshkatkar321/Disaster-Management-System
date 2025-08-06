import { jwtDecode } from 'jwt-decode';
import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import { ReportDisaster } from './services/DisasterService';

export const CreateDisaster = () => {
    const dispath = useDispatch();
    const navigate = useNavigate();

    const token = localStorage.getItem('token');
    const decoded = jwtDecode(token);
    const username = decoded.sub;
    const [validationErrors, setValidationErrors] = useState({});
    const [formData, setFormData] = useState({
        type:'',
        location:'',
        description:'',
        status:'',
        lat:'',
        lng:'',
        user_id:username,
      });

      const [imageFile, setImageFile] = useState(null);

      const handleFileChange = (e) => {
    setImageFile(e.target.files[0]);
  };




  useEffect(() => {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setFormData((prev) => ({
            ...prev,
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          }));
        },
        (error) => {
          console.error('Geolocation error:', error);
        }
      );
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleDisaster = async (e)=>{
    e.preventDefault();

    const dto = {
        ...formData,
    };
    try{
      console.log(dto);
        await ReportDisaster(dto,imageFile)
        // updatedFormData();
        alert("Disaster reported successfully");
        navigate('/user/home');

    }
     catch (err) {
  console.error('reporting failed:', err);

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
    alert("Disaster reporting failed. Please try again.");
  }
}
  }


    return (
        <div>
             <div
      className="container my-5"
      style={{ fontFamily: 'Segoe UI, Roboto, sans-serif', maxWidth: '750px' }}
    >
      <div className="bg-white shadow p-5 rounded-4 border border-primary-subtle">
        <h2 className="text-center mb-4 fw-bold text-primary border-bottom pb-2">
          📋 Report an Incident
        </h2>

        <form onSubmit={handleDisaster}>
          {/* Photo Upload */}
          <div className="mb-4">
            <label className="form-label fw-semibold">Upload Incident Photo</label>
            <input
              type="file"
              accept="image/*"
              className="form-control"
              onChange={handleFileChange}
              
            />
          </div>

          {/* Location */}
          <div className="mb-4">
            {validationErrors.location && (
                <>
                <small className="text-danger">{validationErrors.location}</small>
                <br/>
                </>
              )}
            <label className="form-label fw-semibold">Location</label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              className="form-control"
              placeholder="Enter location of the incident"
              
            />
          </div>

          {/* Disaster Type */}
          <div className="mb-4">
            {validationErrors.type && (
                <>
                <small className="text-danger">{validationErrors.type}</small>
                <br/>
                </>
              )}
            <label className="form-label fw-semibold">Disaster Type</label>
            <input
              type="text"
              name="type"
              value={formData.type}
              onChange={handleChange}
              className="form-control"
              placeholder="e.g., Flood, Earthquake, Fire"
              
            />
          </div>

          {/* Description */}
          <div className="mb-4">
            {validationErrors.description && (
                <>
                <small className="text-danger">{validationErrors.description}</small>
                <br/>
                </>
              )}
            <label className="form-label fw-semibold">Incident Description</label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              className="form-control"
              rows="4"
              placeholder="Briefly describe what happened"
              
            ></textarea>
          </div>

          {/* Status Dropdown */}
          <div className="mb-4">
            {validationErrors.status && (
                <>
                <small className="text-danger">{validationErrors.status}</small>
                <br/>
                </>
              )}
            <label className="form-label fw-semibold">Status</label>
            <select
              className="form-select"
              name='status'
              value={formData.status}
              onChange={handleChange}
              
            >
              <option value="">-- Select Status --</option>
              <option value="active">Active</option>
              <option value="not active">Not Active</option>
            </select>
          </div>


          {/* Submit Button */}
          <div className="d-grid gap-2 mb-3">
            <button
              type="submit"
              className="btn btn-primary fw-bold btn-lg"
            >
              <i className="bi bi-check-circle me-2"></i>Submit Report
            </button>
          </div>

          {/* 🔁 Redirect to Main Page */}
          <div className="text-center">
            <button
              type="button"
              className="btn btn-outline-secondary"
              onClick={() => navigate('/user/home')} // 🔁 Navigate to main page
            >
              <i className="bi bi-house-door me-2"></i>Go to DMS Main Page
            </button>
          </div>
        </form>
      </div>
    </div>
        </div>
    )
}
