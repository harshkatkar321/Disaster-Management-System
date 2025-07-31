import React, { useState,useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { deleteAlertById, updateAlertById } from './services/CreateAlertService';
import { AdminBYName } from '../Admin/services/AdminByUsername';
import { jwtDecode } from 'jwt-decode';


export const AlertUpdate = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  console.log("State ",state);

  const [formData, setFormData] = useState({
    ...state,
    userId: state?.userId || "null",
    disasterId: state?.disaster_id || state?.disasterId || "null",
    tags: state?.tags || [],
  });

  const [tagInput, setTagInput] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleAddTag = () => {
    if (tagInput.trim() !== "") {
      setFormData(prev => ({
        ...prev,
        tags: [...prev.tags, tagInput.trim()],
      }));
      setTagInput("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        console.log("formData",formData);
      await updateAlertById(formData.alertId, formData);
      alert("Alert updated successfully!");
      navigate("/admin/alerts");
    } catch (error) {
      alert("Failed to update alert.");
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
                        // userid : state?.userid || "",
                        // type: state?.type || "",
                        // location: state?.location || "",
                        // description: state?.description || "",
                        // disasterId: state?.disasterId || "",
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

  return (
    <div className="container mt-4">
      <h3>Update Alert</h3>
      <form onSubmit={handleSubmit}>
        <input name="type" value={formData.type} onChange={handleChange} className="form-control mb-2" required />
        <input name="location" value={formData.location} onChange={handleChange} className="form-control mb-2" required />
        <textarea name="description" value={formData.description} onChange={handleChange} className="form-control mb-2" required />
        <select name="severity" value={formData.severity} onChange={handleChange} className="form-control mb-2" required>
          <option value="">Select Severity</option>
          <option value="LOW">LOW</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="HIGH">HIGH</option>
        </select>
        <input name="region" value={formData.region} onChange={handleChange} className="form-control mb-2" required />
        <input type="number" name="riskScore" value={formData.riskScore} onChange={handleChange} className="form-control mb-2" required />
        <textarea name="message" value={formData.message} onChange={handleChange} className="form-control mb-2" required />
        
        <div className="input-group mb-2">
          <input value={tagInput} onChange={(e) => setTagInput(e.target.value)} className="form-control" />
          <button type="button" onClick={handleAddTag} className="btn btn-primary">Add Tag</button>
        </div>

        <div className="mb-3">
          <strong>Tags:</strong> {formData.tags.map((tag, i) => <span key={i} className="badge bg-info me-1">{tag}</span>)}
        </div>

        <button type="submit" className="btn btn-success">Update Alert</button>
      </form>
    </div>
  );
};
