// src/Common/services/ForgotPasswordService.js
import javaAxios from '../../Axios/java-axios';

export async function sendResetEmail(email) {
  try {
    const response = await javaAxios.post('/auth/forgot-password', { email });
    return response.data;
  } catch (err) {
    throw err;
  }
}
