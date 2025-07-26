import React, { useState } from 'react'
import axios from 'axios'
import { NavigationBar } from '../assets/NavigationBar';
import { Footer } from '../assets/Footer';

export const Update = () => {

    const [userData,setUserData] = useState({
        firstName:'',
        lastName:'',
        email:'',
        password:'',
        location:'',
        contactNumber:'',
    });

    const [profilePicture,setProfilePicture] = useState(null);

    const handleChange=(e)=>{
        const {name,value} = e.target;
        setUserData({...userData,[name]: value});
    };

    const handleFileChange = (e) =>{
        setProfilePicture(e.target.files[0]);
    };

    const handleSubmit = async (e) =>{
        e.preventDefault();
    

    const formData = new FormData();
    formData.append('firstName',userData.firstName);
    formData.append('lastName',userData.lastName);
    formData.append('email',userData.email);
    formData.append('password',userData.password);
    formData.append('location',userData.location);
    formData.append('contactNumber',userData.contactNumber);
    if(profilePicture){
        formData.append('profilePicture',profilePicture);
    }

    try{
        const response  = await axios.put(
            'url',
            formData,
            {
                headers:{'Content-Type':'multipart/form-data'}
            }
        );
        console.log('User updated successfully',response.data);
        alert("User updated successfully");
    }
    catch(error){
        console.error('Error updating user:',error);
        alert("Failed to update user");
    }
    };

    return (
        <>
        {/* <NavigationBar/> */}
            <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-4 shadow" style={{ width: '100%', maxWidth: '500px' }}>
        <h3 className="text-center mb-4">Update User Details</h3>
        <form>
          <div className="mb-3">
            <label>First Name</label>
            <input type="text" className="form-control" placeholder="First Name" required />
          </div>
          <div className="mb-3">
            <label>Last Name</label>
            <input type="text" className="form-control" placeholder="Last Name" required />
          </div>
          <div className="mb-3">
            <label>Email</label>
            <input type="email" className="form-control" placeholder="Email" required />
          </div>
          <div className="mb-3">
            <label>Password</label>
            <input type="password" className="form-control" placeholder="Password" required />
          </div>
          <div className="mb-3">
            <label>Location</label>
            <input type="text" className="form-control" placeholder="Location" required />
          </div>
          <div className="mb-3">
            <label>Contact Number</label>
            <input type="tel" className="form-control" placeholder="Contact no." required />
          </div>
          <div className="mb-3">
            <label>Profile Picture</label>
            <input type="file" className="form-control" accept="image/*" />
          </div>
          <div className="d-grid">
            <button type="submit" className="btn btn-success">Register</button>
          </div>
        </form>
      </div>
    </div>
    <Footer/>
        </>
    )
}
