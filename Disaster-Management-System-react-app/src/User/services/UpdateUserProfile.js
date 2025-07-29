import javaAxios from "../../Axios/java-axios";

async function updateUserProfile(id, updatUserDto, imageFile) {
  const formData = new FormData();
  formData.append("updatUserDto", new Blob([JSON.stringify(updatUserDto)], { type: "application/json" }));
  
  if (imageFile) {
    formData.append("imageFile", imageFile);
  }

  try {
    const response = await javaAxios.put(`/users/${id}`, formData);
    return response.data;
  } catch (err) {
    console.log("request data",formData.values());
    console.error("Error updating user profile:", err);
    throw err;
  }
}

export { updateUserProfile };
