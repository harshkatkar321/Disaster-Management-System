import javaAxios from "../../Axios/java-axios";

async function ResourceByKind(kind){
    try{
        const response = await javaAxios.get(`/resources/${kind}`);
        return response.data;
    }
    catch(error){
        console.error("List disaster error : ",error);
        
    }
}

export { ResourceByKind }