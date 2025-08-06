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

async function registerRequest(dto, imageFile) {
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(dto)], {
    type: 'application/json',
  });
  formData.append('dto', jsonBlob);

  if (imageFile) {
    formData.append('imageFile', imageFile);
  }

  try {
    const response = await loginAxios.post('/v1/register', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (err) {
    console.error('Failed to register user:', err);
    throw err;
  }
}

async function verifyOtpRequest(email, otp) {
  try {
    const response = await loginAxios.post('/v1/verify-otp', {
      email,
      otp,
    });
    return response.data;
  } catch (err) {
    console.error('OTP verification failed:', err);
    throw err;
  }
}

export { loginRequest, registerRequest, verifyOtpRequest };
