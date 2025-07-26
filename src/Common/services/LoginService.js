import javaAxios from './../../Axios/java-axios';

async function loginRequest(loginDto) {
  try {
    const response = await javaAxios.post('/v1/login', loginDto);
    return response.data;
  } catch (err) {
    console.error('Failed to Login:', err);
    throw err;
  }
}

export { loginRequest };
