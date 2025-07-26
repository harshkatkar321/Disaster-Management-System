import React, { useState } from 'react'
import { Navbar, Container,Nav } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { Footer } from '../../assets/Footer'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

export const Login = () => {

  const navigate=useNavigate()
  const [username,setUsername] = useState("");
  const [password,setPassword] = useState("");

  const handlelogin = async (e)=>{
    e.preventDefault();
  

  try{
    const response = await axios.post("https://localhost:35729/api/v1/login",{
      username,
      password,
    });
    const token = response.data.token;
    localStorage.setItem("token",token);
    alert("Login Successfull");
    navigate("dashboard");
  }
  catch(error){
    console.error(error);
    alert("Invalid email id or password");
  }
}

    return (
        <>
         <Navbar bg="info" data-bs-theme="light">
        <Container>
          <Navbar.Brand as={Link} to="/">DMS</Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/register">Register</Nav.Link>
            <Nav.Link as={Link} to="/login">Login</Nav.Link>
            <Nav.Link as={Link} to="/about">About</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
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
            <button type="submit" className="btn btn-success">Login</button>
          </div>
        </form>
      </div>
    </div>
    <Footer/>
        </>
    )
}
