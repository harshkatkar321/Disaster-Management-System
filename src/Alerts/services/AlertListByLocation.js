import javaAxios from "../../Axios/java-axios";

async function AlertsByLocation(location){
    try{
        const response = await javaAxios.get(`/alerts/location/${location}`);
        return response.data;
    }
    catch(error){
        console.error("Alert List error ",error);
    }
}

export { AlertsByLocation }