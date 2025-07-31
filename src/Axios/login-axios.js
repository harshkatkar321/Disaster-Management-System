import axios from 'axios';

const loginAxios = axios.create({
  baseURL: 'http://192.168.1.14:8081/api',

});



loginAxios.interceptors.request.use((config) => {
//   config.headers['Content-Type'] = 'application/json';
  return config;
});

export default loginAxios;
