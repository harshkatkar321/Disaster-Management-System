import javaAxios from "../../Axios/java-axios";

async function AdminBYName(username){

    try{
        const data = await javaAxios.get(`/admin/name/${username}`);
        return data;
    }
    catch(error){
        console.error("AdminByName",error);
    }
    
}
export {AdminBYName}