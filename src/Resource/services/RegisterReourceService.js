import javaAxios from "../../Axios/java-axios";

async function RegisterReourceRequest(dto, imageFile) {
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(dto)], {
    type: 'application/json',
  });
  formData.append('dto', jsonBlob);

  if (imageFile) {
    formData.append('imageFile', imageFile);
  }

  try {
    const response = await javaAxios.post('/v1/resource', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (err) {
    console.error('Failed to register user:', err);
    throw err;
  }
}

export { RegisterReourceRequest }
