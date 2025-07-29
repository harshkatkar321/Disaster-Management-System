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

  // Append JSON object as a Blob so Spring @RequestPart can parse it
  const jsonBlob = new Blob([JSON.stringify(dto)], {
    type: 'application/json'
  });
  formData.append('dto', jsonBlob);

  // Attach file if available
  if (imageFile) {
    formData.append('imageFile', imageFile);
  }

  try {
    const response = await loginAxios.post('/v1/register', formData, {
      headers: {
        // Let Axios/browsers set the boundary automatically
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data;
  } catch (err) {
    console.error('Failed to register user:', err);
    throw err;
  }
}

export { loginRequest, registerRequest };
