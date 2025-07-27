import axios from 'axios';

const javaAxios = axios.create({
  baseURL: 'http://localhost:8080/api',

});

javaAxios.interceptors.request.use((config) => {
  config.headers = {
    'Content-Type': 'application/json',
  };
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default javaAxios;
