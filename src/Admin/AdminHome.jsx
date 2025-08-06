import React from 'react';
import { AlertByLocation } from '../Alerts/AlertByLocation';
import { FaBell, FaShieldAlt, FaMapMarkerAlt, FaUserCog, FaListAlt, FaTasks } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export const AdminHome = () => {
  const navigate = useNavigate();

  return (
    <div className="container-fluid px-4 py-4">
      {/* Header Section */}
      <div className="d-flex flex-column flex-md-row justify-content-between align-items-start align-items-md-center mb-4 gap-3">
        <div>
          <h1 className="fw-bold text-primary mb-2">
            <FaShieldAlt className="me-2" />
            Admin Dashboard
          </h1>
          <p className="text-muted mb-0">Welcome back! Admin and manage disaster alerts in real-time.</p>
        </div>
        <div className="d-flex align-items-center bg-primary bg-opacity-10 rounded-pill px-3 py-2 text-primary">
          <FaBell className="me-2" />
          <span className="fw-semibold">Live Alerts</span>
        </div>
      </div>

      {/* Quick Access Cards Row */}
      <div className="row g-3 mb-4">
        <div className="col-md-4">
          <div 
            className="card h-100 border-0 shadow-sm bg-info bg-opacity-10 hover-cursor"
            onClick={() => navigate('/disaster/list')}
          >
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <h6 className="text-uppercase text-info opacity-75 mb-1">Disaster List</h6>
                  <h2 className="mb-0">Manage</h2>
                </div>
                <div className="bg-info bg-opacity-25 rounded-circle p-3">
                  <FaListAlt className="text-info" size={20} />
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div className="col-md-4">
          <div 
            className="card h-100 border-0 shadow-sm bg-warning bg-opacity-10 hover-cursor"
            onClick={() => navigate('/alert/list')}
          >
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <h6 className="text-uppercase text-warning opacity-75 mb-1">Manage Alerts</h6>
                  <h2 className="mb-0">Control</h2>
                </div>
                <div className="bg-warning bg-opacity-25 rounded-circle p-3">
                  <FaTasks className="text-warning" size={20} />
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div className="col-md-4">
          <div 
            className="card h-100 border-0 shadow-sm bg-secondary bg-opacity-10 hover-cursor"
            onClick={() => navigate('/admin/profile')}
          >
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div>
                  <h6 className="text-uppercase text-secondary opacity-75 mb-1">Admin Profile</h6>
                  <h2 className="mb-0">Settings</h2>
                </div>
                <div className="bg-secondary bg-opacity-25 rounded-circle p-3">
                  <FaUserCog className="text-secondary" size={20} />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Main Alert Section */}
      <div className="row">
        <div className="col-12">
          <div className="card shadow-sm border-0">
            <div className="card-header bg-white border-bottom d-flex flex-column flex-md-row justify-content-between align-items-start align-items-md-center gap-2">
              <h5 className="mb-0 text-primary fw-bold d-flex align-items-center">
                <FaMapMarkerAlt className="me-2" />
                Live Alerts by Location
              </h5>
              {/* <div className="btn-group">
                <button className="btn btn-sm btn-outline-primary">Filter</button>
                <button className="btn btn-sm btn-outline-primary">Export</button>
              </div> */}
            </div>
            <div className="card-body p-0">
              <AlertByLocation />
            </div>
            <div className="card-footer bg-white border-0 text-end small text-muted">
              Last updated: {new Date().toLocaleString()}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};