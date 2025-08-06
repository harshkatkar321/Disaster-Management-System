import javaAxios from "../../Axios/java-axios";

async function AdminRegisterRequest(dto, imageFile) {
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(dto)], {
    type: 'application/json'
  });
  formData.append('dto', jsonBlob);

  if (imageFile) {
    formData.append('imageFile', imageFile);
  }

  try {
    const response = await javaAxios.post('/admin', formData); // No manual headers!
    return response.data;
  } catch (err) {
    console.error('Failed to register user:', err);
    throw err;
  }
}

export default  AdminRegisterRequest;