import loginAxios from '../../Axios/login-axios';


async function ReportDisaster(dto, imageFile) {
  const formData = new FormData();
 const token = localStorage.getItem('token');
  // Append JSON object as a Blob so Spring @RequestPart can parse it
  const jsonBlob = new Blob([JSON.stringify(dto)], {
    type: 'application/json'
  });
  formData.append('dto', jsonBlob);
 
  // Attach file if available
  if (imageFile) {
    formData.append('imageFile', imageFile);
  }

  try {
    const response = await loginAxios.post('/disasters', formData, {
      headers: {
        // Let Axios/browsers set the boundary automatically
        'Authorization': `Bearer ${token}`, 
        'Content-Type': 'multipart/form-data',
      }
    });
    return response.data;
  } catch (err) {
    console.error('Failed to register user:', err);
    throw err;
  }
}

export {ReportDisaster};