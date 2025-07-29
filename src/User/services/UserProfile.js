import javaAxios from "../../Axios/java-axios";

async function UserProfile(username){
    try{
        const response = await javaAxios.get(`/users/${username}`);
        return response.data;
    }
    catch(err){
        console.error("User profile error",err);
    }

}
export { UserProfile }