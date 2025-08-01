import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { CreateAlertService } from './services/CreateAlertService';
import { jwtDecode } from 'jwt-decode';
import { AdminBYName } from '../Admin/services/AdminByUsername';

export const AlertCreate = () => {
    const {disasterId} = useParams();
    const navigate = useNavigate();

    const { state } = useLocation();
    const [validationErrors, setValidationErrors] = useState({});
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
            const response =  await CreateAlertService(formData);
            setFormData({});
            setValidationErrors([]);
            
            alert("Alert created successfully!");
            navigate("/admin/home");
        }
        catch (err) {
  console.error('Registration failed:', err);

  if (err.response && err.response.data && Array.isArray(err.response.data.errors)) {
    const groupedErrors = {};

    err.response.data.errors.forEach(error => {
      const [field, message] = error.split(":").map(s => s.trim());

      if (field in groupedErrors) {
        groupedErrors[field] += ` | ${message}`;  // concat if multiple errors for same field
      } else {
        groupedErrors[field] = message;
      }
    });

    setValidationErrors(groupedErrors);
  } else {
    alert("Registration failed. Please try again.");
  }
}
    }

    return (
        <div>
            <div className="container mt-4">
      <h3>Create Alert</h3>
      <form onSubmit={handleSubmit}>
        {validationErrors.type && (
                <>
                <small className="text-danger">{validationErrors.type}</small>
                <br/>
                </>
              )}
        <input type='text' name="type" placeholder="Type" className="form-control mb-2" onChange={handleChange} value={state?.type || null}   />

        {validationErrors.location && (
                <>
                <small className="text-danger">{validationErrors.location}</small>
                <br/>
                </>
              )}
        <input type='text' name="location" placeholder="Location" className="form-control mb-2" onChange={handleChange} value={state?.location || null}   />
        
        {validationErrors.description && (
                <>
                <small className="text-danger">{validationErrors.description}</small>
                <br/>
                </>
              )}
        <textarea type='text' name="description" placeholder="Description" className="form-control mb-2" onChange={handleChange} value={state?.description || null}   />

              {validationErrors.severity && (
                <>
                <small className="text-danger">{validationErrors.severity}</small>
                <br/>
                </>
              )}
        <select name="severity" className="form-control mb-2" onChange={handleChange}  >
          <option value="">Select Severity</option>
          <option value="LOW">LOW</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="HIGH">HIGH</option>
        </select>
            
            {validationErrors.region && (
                <>
                <small className="text-danger">{validationErrors.region}</small>
                <br/>
                </>
              )}
        <input name="region" placeholder="Region" className="form-control mb-2" onChange={handleChange}   />

              {validationErrors.riskScore && (
                <>
                <small className="text-danger">{validationErrors.riskScore}</small>
                <br/>
                </>
              )}
        <input type="number" name="riskScore" placeholder="Risk Score" className="form-control mb-2" onChange={handleChange}   />

              {validationErrors.message && (
                <>
                <small className="text-danger">{validationErrors.message}</small>
                <br/>
                </>
              )}
        <textarea name="message" placeholder="Message" className="form-control mb-2" onChange={handleChange}   />

        <div className="input-group mb-2">
            {validationErrors.tags && (
                <>
                <small className="text-danger">{validationErrors.tags}</small>
                <br/>
                </>
              )}
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
