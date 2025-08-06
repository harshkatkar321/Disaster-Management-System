import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import AdminProfileService from '../Admin/services/AdminProfileService';

export const AdminProfilePage = () => {
  const [profile, setProfile] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAdminProfile = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          navigate('/admin/login');
          return;
        }

        const decoded = jwtDecode(token);
        const adminId = decoded.sub;
        
        const data = await AdminProfileService(adminId);
        if (data) {
          setProfile(data);
        } else {
          alert('Failed to load profile');
          navigate('/admin/home');
        }
      } catch (error) {
        console.error('Profile fetch error:', error);
        navigate('/admin/login');
      }
    };

    fetchAdminProfile();
  }, [navigate]);

  if (!profile) {
    return (
      <div className="d-flex justify-content-center mt-5">
        <div className="spinner-border text-primary" role="status" aria-label="Loading">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  const {
    firstName,
    lastName,
    email,
    phoneNumber,
    city,
    imageData,
    imageType,
    role,
    createdAt
  } = profile;

  const profileImage = imageData 
    ? `data:${imageType};base64,${imageData}`
    : '/default-admin.png';

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-8">
          <div className="card shadow-sm rounded">
            <div className="card-header bg-primary text-white py-3 rounded-top d-flex justify-content-between align-items-center">
              <h4 className="mb-0 d-flex align-items-center gap-2">
                <i className="bi bi-person-gear fs-4"></i>
                Admin Profile
              </h4>
              <span className="badge bg-light text-primary fw-semibold text-uppercase px-3 py-2">
                {role}
              </span>
            </div>

            <div className="card-body p-4">
              <div className="row align-items-center">
                {/* Profile Image */}
                <div className="col-md-4 text-center mb-4 mb-md-0">
                  <img
                    src={profileImage}
                    alt={`Profile of ${firstName} ${lastName}`}
                    className="img-fluid rounded-circle border border-3 border-primary"
                    style={{
                      width: '200px',
                      height: '200px',
                      objectFit: 'cover',
                      boxShadow: '0 4px 12px rgba(0, 0, 0, 0.15)',
                      transition: 'transform 0.3s ease'
                    }}
                    onMouseOver={e => (e.currentTarget.style.transform = 'scale(1.05)')}
                    onMouseOut={e => (e.currentTarget.style.transform = 'scale(1)')}
                  />
                </div>

                {/* Profile Details */}
                <div className="col-md-8">
                  <div className="row g-3">
                    <div className="col-md-6">
                      <h6 className="text-muted mb-1 small">First Name</h6>
                      <p className="h5 text-dark fw-semibold">{firstName}</p>
                    </div>

                    <div className="col-md-6">
                      <h6 className="text-muted mb-1 small">Last Name</h6>
                      <p className="h5 text-dark fw-semibold">{lastName}</p>
                    </div>

                    <div className="col-12">
                      <h6 className="text-muted mb-1 small">Email</h6>
                      <p className="h5 text-dark fw-semibold">{email}</p>
                    </div>

                    <div className="col-md-6">
                      <h6 className="text-muted mb-1 small">Phone</h6>
                      <p className="h5 text-dark fw-semibold">{phoneNumber}</p>
                    </div>

                    <div className="col-md-6">
                      <h6 className="text-muted mb-1 small">City</h6>
                      <p className="h5 text-dark fw-semibold">{city}</p>
                    </div>

                    <div className="col-md-6">
                      <h6 className="text-muted mb-1 small">Member Since</h6>
                      <p className="h5 text-dark fw-semibold">
                        {new Date(createdAt).toLocaleDateString('en-US', {
                          year: 'numeric',
                          month: 'short',
                          day: 'numeric'
                        })}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="card-footer bg-light rounded-bottom py-3 text-center">
              <button
                className="btn btn-primary px-4 fw-semibold"
                onClick={() => navigate('/admin/home')}
                aria-label="Back to Dashboard"
                title="Back to Dashboard"
              >
                <i className="bi bi-arrow-left me-2"></i> Back to Dashboard
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
