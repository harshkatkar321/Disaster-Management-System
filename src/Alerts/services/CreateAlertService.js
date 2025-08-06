import javaAxios from "../../Axios/java-axios";

async function CreateAlertService(formData){
    try {
      await javaAxios.post("/alerts", formData); // adjust endpoint
     // or wherever you want
    } catch (error) {
      console.error("Error creating alert:", error);
      throw error;
      
    }
}

export const GetAdminAlerts = async () => {
  try {
    const response = await javaAxios.get(`/alerts`);
    return response.data;
  } catch (error) {
    console.error('Error fetching admin alerts:', error);
    throw error;
  }
};

export const deleteAlertById = (id) => {
  return javaAxios.delete(`/alerts/${id}`);
};

export const updateAlertById = async (alertId, alertData) => {
  try {
    const response = await javaAxios.put(`/alerts/${alertId}`, alertData);
    console.log("Response ",response.data);
    return response.data;
  } catch (error) {
    console.error("Failed to update alert:", error);
    throw error;
  }
};

export { CreateAlertService }