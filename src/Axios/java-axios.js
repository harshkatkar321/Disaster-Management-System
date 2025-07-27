import axios from 'axios';

const javaAxios = axios.create({
  baseURL: 'http://localhost:8080/api',

});



javaAxios.interceptors.request.use((config) => {
  config.headers['Content-Type'] = 'application/json';
  const token = localStorage.getItem('token');
  console.log(token);
  if (token) {
    console.log(token);
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

export default javaAxios;
