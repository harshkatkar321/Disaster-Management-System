import { jwtDecode } from 'jwt-decode';
import React from 'react'

export const UserHome = () => {
    const token = localStorage.getItem('token');
    const decoded = jwtDecode(token);
    
    console.log("Username : ",decoded)
    return (
        <div>
            <h1>Welcome user to DMS</h1>
        </div>
    )
}
