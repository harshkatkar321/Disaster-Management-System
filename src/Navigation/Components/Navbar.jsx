import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { NavLink, useNavigate, useLocation } from 'react-router-dom';
import { logout } from '../../Auth/authSlice';

export const Navbar = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const location = useLocation();

  const user = useSelector((state) => state.auth.user);
  const role = user?.role;
  const token = user?.token;

  useEffect(() => {
    const localtoken = localStorage.getItem('token');
    if (!localtoken && token) {
      dispatch(logout());
      navigate('/', { replace: true });
    }
  }, [token, dispatch, navigate]);

  const handleLogout = () => {
    dispatch(logout());
    navigate('/');
  };

  const navLinkClass = ({ isActive }) => 
    `nav-link mx-1 ${isActive ? 'active fw-bold' : ''}`;

  // 1. Homepage navigation (About, Contact, Login)
  const renderMainPageLinks = () => (
    <>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/about">
          About
        </NavLink>
      </li>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/contact">
          Contact
        </NavLink>
      </li>
    </>
  );

  // 2. User navigation
  const renderUserLinks = () => (
    <>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/user/home">
          Dashboard
        </NavLink>
      </li>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/user/reportdisaster">
          Report Disaster
        </NavLink>
      </li>
    </>
  );

  // 3. Admin navigation
  const renderAdminLinks = () => (
    <>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/admin/home">
          Dashboard
        </NavLink>
      </li>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/disaster/list">
          Disaster List
        </NavLink>
      </li>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/alert/list">
          Manage Alerts
        </NavLink>
      </li>
    </>
  );

  // 4. Super Admin navigation
  const renderSuperAdminLinks = () => (
    <>
      {/* <li className="nav-item">
        <NavLink className={navLinkClass} to="/admin/home">
          Dashboard
        </NavLink>
      </li>
      <li className="nav-item">
        <NavLink className={navLinkClass} to="/super-admin/admin/register">
          Admin Register
        </NavLink>
      </li> */}
    </>
  );

  // Brand/logo with conditional click behavior (5)
  const renderBrand = () => {
    if (token) {
      return <span className="navbar-brand fw-bold fs-3"> DisasterManagement</span>;
    }
    return (
      <NavLink className="navbar-brand fw-bold fs-3" to="/">
        DisasterManagement
      </NavLink>
    );
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary sticky-top shadow">
      <div className="container">
        {renderBrand()}

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#mainNavbar"
          aria-controls="mainNavbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="mainNavbar">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            {!token && location.pathname === '/' && renderMainPageLinks()}
            {token && role === 'USER' && renderUserLinks()}
            {token && role === 'ADMIN' && renderAdminLinks()}
            {token && role === 'SUPER_ADMIN' && renderSuperAdminLinks()}
          </ul>

          <div className="d-flex align-items-center">
            {!token ? (
              // 1. Simple Login button for homepage
              <NavLink className="btn btn-outline-light" to="/login">
                Login
              </NavLink>
            ) : (
              // Logout button for all logged-in users
              <button 
                className="btn btn-outline-light" 
                onClick={handleLogout}
              >
                Logout
              </button>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};