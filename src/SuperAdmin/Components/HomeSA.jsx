import React from 'react'
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import { profile } from './Services/Super-Admin'

export const HomeSA = () => {
    const navigate = useNavigate();

    const token = localStorage.getItem('token');
    if (!token) {
        navigate('/login', { replace: true });
    }

    let superAdmin;
    if (token) {
        const decoded = jwtDecode(token);
        const sub = decoded.sub;
        superAdmin = sub;
    }

    const handleFetchProfile = async () => {
       
        try {
            const data = await profile(superAdmin);
            console.log(data); 
            
        } catch (err) {
            console.error('Error fetching profile:', err);
        } 
    };


    return (
        <div>
            <h1>Welcome Super Admin {superAdmin}</h1>

            <button onClick={handleFetchProfile}>Get Profile</button>
        </div>
    )
}
