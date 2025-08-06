import axios from 'axios';

const loginAxios = axios.create({
  baseURL: 'http://localhost:8080/api',

});



loginAxios.interceptors.request.use((config) => {
//   config.headers['Content-Type'] = 'application/json';
  return config;
});

export default loginAxios;
