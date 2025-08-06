import axios from 'axios';
// http://localhost:8081/api
const javaAxios = axios.create({
  baseURL: 'http://localhost:8080/api',

});

javaAxios.interceptors.request.use((config) => {
  const isFormData = config.data instanceof FormData;

  // Only set Content-Type if not FormData
  if (!isFormData) {
    config.headers['Content-Type'] = 'application/json';
  }
  const token = localStorage.getItem('token');
  console.log(token);
  if (token) {
    console.log(token);
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

export default javaAxios;
