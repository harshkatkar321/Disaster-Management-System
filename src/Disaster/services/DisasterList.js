import javaAxios from "../../Axios/java-axios";

async function DisasterList(){
    try{
        const response = await javaAxios.get("/disasters");
        return response.data;

    }
    catch(error){
        console.error("List disaster error : ",error);
        
    }
}

export { DisasterList }