import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { CreateAlertService } from './services/CreateAlertService';
import { jwtDecode } from 'jwt-decode';
import { AdminBYName } from '../Admin/services/AdminByUsername';

export const AlertCreate = () => {
    const {disasterId} = useParams();
    const navigate = useNavigate();

    const { state } = useLocation();

    const [formData, setFormData] = useState({
        type: "",
        location:  "",
        description: "",
        disasterId:"", // comes from URL
        userId : "",
        adminId :"",
        severity: "",
        region: "",
        riskScore: 0,
        message: "",
        tags: [],
    });

    const [tagInput, setTagInput] = useState("");

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]:value,
        }));
    };

    const handleAddTag = () => {
        if(tagInput.trim() !== ""){
            setFormData((prev) => ({
                ...prev,
                tags: [...prev.tags,tagInput.trim()],
            }));
            setTagInput("");
        }
    };

    useEffect(() => {
        const fetachAdminId = async ()=> {
            try{
                const token  = localStorage.getItem('token');
                const decoded = jwtDecode(token);
                const username = decoded.sub;

                const response = await AdminBYName(username);
                if(response && response.data && response.data.id){
                    

                    setFormData(prev => ({
                        ...prev,
                        adminId :  response.data.id,
                        userid : state?.userid || "",
                        type: state?.type || "",
                        location: state?.location || "",
                        description: state?.description || "",
                        disasterId: state?.disasterId || "",
                    }));
                }
                else{
                    console.error("AdminId not found",response);
                }
                
            }
            catch(error){
                alert("Error fetching adminId");
            }
        }
        fetachAdminId();
    },[]);
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            console.log("formdata",formData);
            await CreateAlertService(formData);
            
            alert("Alert created successfully!");
            navigate("/admin/home");
        }
        catch (error) {
   
      alert("Failed to create alert.");
    }
    }

    return (
        <div>
            <div className="container mt-4">
      <h3>Create Alert</h3>
      <form onSubmit={handleSubmit}>
        <input name="type" placeholder="Type" className="form-control mb-2" onChange={handleChange} value={state?.type || null} required />

        <input name="location" placeholder="Location" className="form-control mb-2" onChange={handleChange} value={state?.location || null} required />

        <textarea name="description" placeholder="Description" className="form-control mb-2" onChange={handleChange} value={state?.description || null} required />

        <select name="severity" className="form-control mb-2" onChange={handleChange} required>
          <option value="">Select Severity</option>
          <option value="LOW">LOW</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="HIGH">HIGH</option>
        </select>

        <input name="region" placeholder="Region" className="form-control mb-2" onChange={handleChange} required />

        <input type="number" name="riskScore" placeholder="Risk Score" className="form-control mb-2" onChange={handleChange} required />

        <textarea name="message" placeholder="Message" className="form-control mb-2" onChange={handleChange} required />

        <div className="input-group mb-2">
          <input value={tagInput} onChange={(e) => setTagInput(e.target.value)} placeholder="Add Tag" className="form-control" />
          <button type="button" onClick={handleAddTag} className="btn btn-primary">Add</button>
        </div>

        <div className="mb-3">
          <strong>Tags:</strong> {formData.tags.map((tag, i) => <span key={i} className="badge bg-info me-1">{tag}</span>)}
        </div>

        <button type="submit" className="btn btn-success">Submit Alert</button>
      </form>
    </div>
        </div>
    )
}
