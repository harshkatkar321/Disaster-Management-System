import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import { CreateAlertService } from './services/CreateAlertService';
import { jwtDecode } from 'jwt-decode';
import { AdminBYName } from '../Admin/services/AdminByUsername';
import { ResourceByKind } from '../Resource/services/ResourceByType';
import toast from 'react-hot-toast';

export const AlertCreate = () => {
    const {disasterId} = useParams();
    const navigate = useNavigate();

    const { state } = useLocation();
    const [resourceKind, setResourceKind] = useState("");
    const [availableResources, setAvailableResources] = useState([]);
    const [selectedResources, setSelectedResources] = useState([]);
    const [validationErrors, setValidationErrors] = useState({});
    const initialFormData = {
    type: "",
    location: "",
    description: "",
    disasterId: "",
    userId: "",
    adminId: "",
    severity: "",
    region: "",
    riskScore: 0,
    message: "",
    tags: [],
};
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
        resourceIds:[]
    });

    const [tagInput, setTagInput] = useState("");

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]:value,
        }));
    };

    const handleResourceKindChange = async (e) => {
  const kind = e.target.value;
  setResourceKind(kind);
  
  try {
    const response = await  ResourceByKind(kind);
    console.log(response);
    setAvailableResources(Array.isArray(response) ? response : []); // assume response.data is a list of resources
  } catch (error) {
    console.error("Failed to fetch resources:", error);
    setAvailableResources([]);
  }
};

const handleResourceSelect = (e) => {
  const selectedId = e.target.value;
  const selected = availableResources.find(res => res.id === selectedId);
  
  if (selected && !selectedResources.some(res => res.id === selected.id)) {
    setSelectedResources(prev => [...prev, selected]);
    setFormData(prev => ({
      ...prev,
      resourceIds: [...(prev.resourceIds || []), selected.id]
    }));
  }
};

const removeSelectedResource = (id) => {
  setSelectedResources(prev => prev.filter(res => res.id !== id));
  setFormData(prev => ({
    ...prev,
    resourceIds: prev.resourceIds.filter(rid => rid !== id)
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
            setFormData(initialFormData);
            setValidationErrors([]);
            
            // alert("Alert created successfully!");
            
            
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
        <input type='text' name="type" placeholder="Type" className="form-control mb-2" onChange={handleChange} value={state?.type || ""}   />

        {validationErrors.location && (
                <>
                <small className="text-danger">{validationErrors.location}</small>
                <br/>
                </>
              )}
        <input type='text' name="location" placeholder="Location" className="form-control mb-2" onChange={handleChange} value={state?.location || ""}   />
        
        {validationErrors.description && (
                <>
                <small className="text-danger">{validationErrors.description}</small>
                <br/>
                </>
              )}
        <textarea type='text' name="description" placeholder="Description" className="form-control mb-2" onChange={handleChange} value={state?.description || ""}   />

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

        <hr />
        <h5>Add Resources to Alert</h5>

        <select className="form-control mb-2" onChange={handleResourceKindChange} value={resourceKind}>
          <option value="">Select Resource Type</option>
          <option value="PERSONNEL">PERSONNEL</option>
          <option value="TEAM">TEAM</option>
          <option value="EQUIPMENT">EQUIPMENT</option>
          <option value="SUPPLY">SUPPLY</option>
          <option value="FACILITY">FACILITY</option>
        </select>

        {(availableResources?.length ?? 0) > 0 && (
          <select className="form-control mb-2" onChange={handleResourceSelect}>
            <option value="">Select Resource</option>
            {availableResources.map(res => (
              <option key={res.id} value={res.id}>{res.name} - {res.city}</option>
            ))}
          </select>
        )}

        {selectedResources.length > 0 && (
          <div className="mb-3">
            <strong>Selected Resources:</strong>
            <ul className="list-group">
              {selectedResources.map(res => (
                <li key={res.id} className="list-group-item d-flex justify-content-between align-items-center">
                  {res.name} ({res.type})
                  <button type="button" className="btn btn-sm btn-danger" onClick={() => removeSelectedResource(res.id)}>Remove</button>
                </li>
              ))}
            </ul>
          </div>
        )}


        <button type="submit" className="btn btn-success">Submit Alert</button>
      </form>
    </div>
        </div>
    )
}
