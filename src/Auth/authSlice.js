import { createSlice } from '@reduxjs/toolkit';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

const tokeFromStorage = localStorage.getItem('token');
let initialUser=null;


if(tokeFromStorage){
  try{
    const decoded = jwtDecode(tokeFromStorage);
    initialUser = {
      token : tokeFromStorage,
      role : decoded.roles?.[0] || null,
    }
  }
  catch(err){
    console.error("Failed to decode token",err);
  }
}

const authSlice = createSlice({
  
  name: 'auth',
  initialState : {
    user : initialUser,
  },
  reducers: {
    loginSuccess: (state, action) => {
        const token = action.payload;
        
        if(typeof token === 'string' && token.trim()!== ''){
            // state.token = action.payload;
            localStorage.setItem('token', token);

        
     

      try {
        const decoded = jwtDecode(token);
      //   state.role = decoded.roles?.[0] || null; // assuming roles is array
      // } catch (err) {
      //   state.token = null;
      //   state.role = null;
      //   console.error("JWT decode failed", err);
      state.user = {
        token : token,
        role : decoded.roles?.[0] || null,
      };
      }catch(error){
        console.error("JWT decode failed", err);
        state.user = null;
      }
    }else{
        console.error("Invalid token passed or loginSuccess",token);
    }
    },
    logout: (state) => {
      // state.token = null;
      // state.role = null;
      localStorage.removeItem('token');
      state.user = null;
      
      
    },
  },
});

export const { loginSuccess, logout } = authSlice.actions;
export default authSlice.reducer;
