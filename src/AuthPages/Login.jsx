import React, { useState } from 'react'
import { NavigationBar } from '../assets/NavigationBar'
import { Footer } from '../assets/Footer'
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
        <NavigationBar/>
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
