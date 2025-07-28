import { jwtDecode } from 'jwt-decode';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { NavLink, replace, useNavigate } from 'react-router-dom';
import { logout } from '../../Auth/authSlice';

export const Navbar = () => {

  const navigate = useNavigate();
  // const [role,setRole] = useState(null);
  const dispatch = useDispatch();


  const user = useSelector((state) => state.auth.user);
  const role = user?.role;
  const token = user?.token;
  

  useEffect(()=>{
    const localtoken = localStorage.getItem('token');
    if(!localtoken && token){
      dispatch(logout());
      navigate('/',{replace : true});
    }
  },[token]);

   const handleLogout = () => {
    dispatch(logout());
    // localStorage.removeItem('token');
    navigate('/');
  };

  const renderLinks = ()=>{
    if(!role) return null;

    switch(role){
      case 'ADMIN':
        return(
          <>
          <li className="nav-item">
              <NavLink className="nav-link" to="/admin/home">Dashboard</NavLink>
            </li>
          <li className="nav-item">
              <NavLink className="nav-link" to="/disaster/list">DisasterList</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/admin/alert/create"><strong>Create Alerts</strong></NavLink>
            </li>
          </>
        );
      case 'SUPER_ADMIN':
        return(
          <>
          <li className="nav-item">
              <NavLink className="nav-link" to="/admin-dashboard">Dashboard</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/super-admin/admin/register">Admin Register</NavLink>
            </li>
          </>
        );
        case 'USER':
          return (
            <>
             <li className="nav-item">
              <NavLink className="nav-link" to="/user/profile">Profile</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/user/reportdisaster">Report Disaster</NavLink>
            </li>
            </>
          );
          default:
            return null;
    }

  };

 

   


  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-warning">
      <div className="container-fluid">
        <NavLink className="navbar-brand fw-bold" to="/">
          MyApplication
        </NavLink>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <NavLink className="nav-link" to="/home">
                Home
              </NavLink>
            </li>
            {token && renderLinks()}
           {
            <>
             <li className="nav-item">
              <NavLink className="nav-link" to="/about">
                About
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/contact">
                Contact
              </NavLink>
            </li>
            </>
           }
           
            
          </ul>
         <div className="d-flex align-items-center gap-2">
            {token ? (
              <button className="btn btn-outline-danger" onClick={handleLogout}>Logout</button>
            ) : (
              <>
                <NavLink className="btn btn-outline-primary" to="/login">Login</NavLink>
                <NavLink className="btn btn-outline-success" to="/register">Register</NavLink>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};
