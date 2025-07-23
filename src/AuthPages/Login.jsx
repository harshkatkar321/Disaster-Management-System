import React from 'react'
import { NavbarStatic } from '../components/NavbarStatic'

export const Login = () => {
    return (
        <>
        <NavbarStatic/>
            <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-4 shadow" style={{ width: '100%', maxWidth: '500px' }}>
        <h3 className="text-center mb-4">Login</h3>
        <form>
         
          <div className="mb-3">
            <label>Email</label>
            <input type="email" className="form-control" placeholder="Email" required />
          </div>
          <div className="mb-3">
            <label>Password</label>
            <input type="password" className="form-control" placeholder="Password" required />
          </div>
         
          <div className="d-grid">
            <button type="submit" className="btn btn-success">Register</button>
          </div>
        </form>
      </div>
    </div>
        </>
    )
}
