import axios from 'axios';

export const sendResetEmail = async (email) => {
  try {
    const response = await axios.post('/api/auth/forgot-password', { email });
    return response.data;
  } catch (error) {
    throw error;
  }
};
