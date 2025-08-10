import javaAxios from "../../Axios/java-axios";

async function getNotVerifiedResourcesByCity(city){
  const response = await javaAxios.get(`resources/notverified/city/${city}`);
  return response.data;
};

async function verifyResource(resourceId){
  const response = await javaAxios.put(`/resources/${resourceId}`);
  return response.data;
};

export { getNotVerifiedResourcesByCity, verifyResource }