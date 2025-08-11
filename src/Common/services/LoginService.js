import javaAxios from './../../Axios/java-axios';
import loginAxios from '../../Axios/login-axios';

async function loginRequest(loginDto) {
  try {
    const response = await loginAxios.post('/v1/login', loginDto);
    return response.data;
  } catch (err) {
    console.error('Failed to Login:', err);
    throw err;
  }
}

// async function registerRequest(dto, imageFile) {
//   const formData = new FormData();

//   const jsonBlob = new Blob([JSON.stringify(dto)], {
//     type: 'application/json',
//   });
//   formData.append('dto', jsonBlob);

//   if (imageFile) {
//     formData.append('imageFile', imageFile);
//   }

//   try {
//     const response = await loginAxios.post('/v1/register', formData, {
//       headers: {
//         'Content-Type': 'multipart/form-rdata',
//       },
//     });
//     return response.data;
//   } catch (err) {
//     console.error('Failed to register user:', err);
//     throw err;
//   }
// }

async function registerRequest(dto, imageFile) {
  const formData = new FormData();
  const jsonBlob = new Blob([JSON.stringify(dto)], { type: 'application/json' });
  formData.append('dto', jsonBlob);

  if (imageFile) formData.append('imageFile', imageFile);

  try {
    const response = await loginAxios.post('/v1/register', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
  } catch (err) {
    const resp = err.response;
    if (resp && resp.data && Array.isArray(resp.data.errors)) {
      // Extract validation errors and throw
      const messages = resp.data.errors.map(e => e.message || JSON.stringify(e));
      throw new Error(`Validation failed: ${messages.join(', ')}`);
    }
    if (resp && resp.status) {
      throw new Error(`Server responded with status ${resp.status}`);
    }
    throw err; // network or other unexpected error
  }
}

async function verifyOtpRequest(dto) {

  try {
    const response = await loginAxios.post('/v1/verifyaccount',dto);
    return response.data;
  } catch (err) {
    console.error('OTP verification failed:', err);
    throw err;
  }
}

async function RegenerateOtpRequest(formData) {
  try {
    const response = await loginAxios.post('/v1/regenerateotp', {
      formData
    });
    return response.data;
  } catch (err) {
    console.error('OTP regeneration failed:', err);
    throw err;
  }
}


async function ForgotPasswordWithOtp(dto){
  try {
    const response = await loginAxios.post('/v1/forgotpasswordotp',dto);
    return response.data;
  } catch (err) {
    console.error('OTP verification failed:', err);
    throw err;
  }

}

async function ChangePassword(dto){
  try {
    const response = await loginAxios.post('/v1/setNewPassword',dto);
    return response.data;
  } catch (err) {
    console.error('OTP verification failed:', err);
    throw err;
  }

}

export { loginRequest, registerRequest, verifyOtpRequest,RegenerateOtpRequest, ForgotPasswordWithOtp,ChangePassword };
