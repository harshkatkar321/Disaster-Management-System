import javaAxios from './../../../Axios/java-axios';

async function profile(username) {
  try {
    const response = await javaAxios.get(`/super-admin/${username}`);
    return response.data;
  } catch (err) {
    console.error('Failed to Login:', err);
    throw err;
  }
}

export { profile };