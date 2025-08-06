import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { profile } from './Services/Super-Admin';

export const HomeSA = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [profileData, setProfileData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login', { replace: true });
      return;
    }

    try {
      const decoded = jwtDecode(token);
      setUsername(decoded.sub);
    } catch (err) {
      console.error('Invalid token:', err);
      navigate('/login', { replace: true });
    }
  }, [navigate]);

  const fetchProfile = async () => {
    if (!username) return;

    setLoading(true);
    setError('');
    try {
      const data = await profile(username);
      setProfileData(data);
    } catch (err) {
      console.error('Failed to fetch profile:', err);
      setError('Failed to fetch profile. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-8">
          {/* Header Section */}
          <div className="d-flex justify-content-between align-items-center mb-5">
            <div>
              <h1 className="mb-1">
                <i className="bi bi-shield-check me-2 text-primary"></i>
                Super Admin Dashboard
              </h1>
              <p className="text-muted mb-0">Welcome back, {username}</p>
            </div>
            <div className="d-flex">
              <button 
                className="btn btn-outline-secondary me-2"
                onClick={() => navigate('/admin/settings')}
              >
                <i className="bi bi-gear me-1"></i> Settings
              </button>
              <button 
                className="btn btn-outline-danger"
                onClick={() => {
                  localStorage.removeItem('token');
                  navigate('/login');
                }}
              >
                <i className="bi bi-box-arrow-right me-1"></i> Logout
              </button>
            </div>
          </div>

          {/* Profile Card */}
          <div className="card shadow-sm border-0 mb-4">
            <div className="card-header bg-white border-bottom-0 py-3">
              <h4 className="mb-0">
                <i className="bi bi-person-badge me-2 text-primary"></i>
                Admin Profile
              </h4>
            </div>
            <div className="card-body">
              {loading ? (
                <div className="text-center py-4">
                  <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                  </div>
                  <p className="mt-2">Loading profile data...</p>
                </div>
              ) : error ? (
                <div className="alert alert-danger">
                  <i className="bi bi-exclamation-triangle-fill me-2"></i>
                  {error}
                </div>
              ) : profileData ? (
                <div className="row">
                  <div className="col-md-4 text-center mb-4 mb-md-0">
                    <div className="bg-light rounded-circle d-flex align-items-center justify-content-center mx-auto" 
                         style={{ width: '150px', height: '150px' }}>
                      <i className="bi bi-person-circle fs-1 text-muted"></i>
                    </div>
                  </div>
                  <div className="col-md-8">
                    <div className="row">
                      <div className="col-md-6 mb-3">
                        <h6 className="text-muted mb-1">Full Name</h6>
                        <p className="fs-5">{profileData.firstName} {profileData.lastName}</p>
                      </div>
                      <div className="col-md-6 mb-3">
                        <h6 className="text-muted mb-1">Username</h6>
                        <p className="fs-5">{profileData.username}</p>
                      </div>
                      <div className="col-md-6 mb-3">
                        <h6 className="text-muted mb-1">Email</h6>
                        <p className="fs-5">{profileData.email}</p>
                      </div>
                      <div className="col-md-6 mb-3">
                        <h6 className="text-muted mb-1">Phone</h6>
                        <p className="fs-5">{profileData.phoneNumber || 'N/A'}</p>
                      </div>
                      <div className="col-md-6 mb-3">
                        <h6 className="text-muted mb-1">City</h6>
                        <p className="fs-5">{profileData.city || 'N/A'}</p>
                      </div>
                    </div>
                  </div>
                </div>
              ) : (
                <div className="text-center py-4">
                  <i className="bi bi-person-x fs-1 text-muted mb-3"></i>
                  <h5>No profile data available</h5>
                  <p className="text-muted">Click the button below to fetch your profile information</p>
                  <button
                    className="btn btn-primary px-4"
                    onClick={fetchProfile}
                    disabled={loading || !username}
                  >
                    <i className="bi bi-download me-2"></i>
                    {loading ? 'Loading...' : 'Get Profile'}
                  </button>
                </div>
              )}
            </div>
          </div>

          {/* Quick Actions */}
          <div className="row">
            <div className="col-md-4 mb-3">
              <div className="card h-100 border-0 shadow-sm">
                <div className="card-body text-center">
                  <div className="bg-primary bg-opacity-10 rounded-circle d-inline-flex p-3 mb-3">
                    <i className="bi bi-people-fill fs-3 text-primary"></i>
                  </div>
                  <h5>Manage Admins</h5>
                  <p className="text-muted small">View and manage all admin accounts</p>
                  <button 
                    className="btn btn-outline-primary btn-sm"
                    onClick={() => navigate('/super-admin/admin/register')}
                  >
                    Go to Admin Management
                  </button>
                </div>
              </div>
            </div>
            <div className="col-md-4 mb-3">
              <div className="card h-100 border-0 shadow-sm">
                <div className="card-body text-center">
                  <div className="bg-primary bg-opacity-10 rounded-circle d-inline-flex p-3 mb-3">
                    <i className="bi bi-shield-exclamation fs-3 text-primary"></i>
                  </div>
                  <h5>System Alerts</h5>
                  <p className="text-muted small">View and manage system alerts</p>
                  <button 
                    className="btn btn-outline-primary btn-sm"
                    onClick={() => navigate('/alert/list')}
                  >
                    View Alerts
                  </button>
                </div>
              </div>
            </div>
            <div className="col-md-4 mb-3">
              <div className="card h-100 border-0 shadow-sm">
                <div className="card-body text-center">
                  <div className="bg-primary bg-opacity-10 rounded-circle d-inline-flex p-3 mb-3">
                    <i className="bi bi-graph-up fs-3 text-primary"></i>
                  </div>
                  <h5>Reports</h5>
                  <p className="text-muted small">View system reports and analytics</p>
                  <button 
                    className="btn btn-outline-primary btn-sm"
                    onClick={() => navigate('/reports')}
                  >
                    View Reports
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};