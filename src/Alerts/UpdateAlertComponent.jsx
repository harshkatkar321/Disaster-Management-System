import { useLocation, useNavigate } from "react-router-dom";
import { ResourceByKind } from "../Resource/services/ResourceByType";
import { updateAlertById } from "./services/CreateAlertService";
import { jwtDecode } from "jwt-decode";
import { AdminBYName } from "../Admin/services/AdminByUsername";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

export const AlertUpdate = () => {
  const { state } = useLocation();
  const navigate = useNavigate();

  const [resourceKind, setResourceKind] = useState("");
  const [availableResources, setAvailableResources] = useState([]);
  const [selectedResources, setSelectedResources] = useState([]); // holds already assigned + newly selected

  const [formData, setFormData] = useState({
    ...state,
    userId: state?.userId || "null",
    disasterId: state?.disaster_id || state?.disasterId || "null",
    tags: state?.tags || [],
    resources: state?.resources || [],
    resourceIds: state?.resources?.map(r => r.id) || [] // initialize resourceIds
  });

  const [tagInput, setTagInput] = useState("");

  // ⬇ Populate selectedResources from state on first render
  useEffect(() => {
  if (state?.resources) {
    setSelectedResources(state.resources);
    setFormData(prev => ({
      ...prev,
      resources: state.resources,
      resourceIds: state.resources.map(r => r.id)
    }));
  }
}, [state]);


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

  const handleResourceKindChange = async (e) => {
    const kind = e.target.value;
    setResourceKind(kind);
    try {
      const response = await ResourceByKind(kind);
      setAvailableResources(Array.isArray(response) ? response : []);
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
        resourceIds: [...(prev.resourceIds || []), selected.id],
         resources: [...(prev.resources || []), selected]
      }));
    }
  };

const removeSelectedResource = (id) => {
  const idStr = String(id);

  console.log("Removing resource:", idStr);

  setSelectedResources(prev => prev.filter(res => String(res.id) !== idStr));

  setFormData(prev => {
    const updated = {
      ...prev,
      resources: prev.resources.filter(res => String(res.id) !== idStr),
      resourceIds: prev.resourceIds.filter(rid => String(rid) !== idStr)
    };
    console.log("Updated formData:", updated);
    return updated;
  });
};



  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateAlertById(formData.alertId, formData);
      toast.success("Alert updated successfully!");
      navigate("/admin/home");
    } catch (error) {
      alert("Failed to update alert.");
    }
  };

  useEffect(() => {
    const fetachAdminId = async () => {
      try {
        const token = localStorage.getItem('token');
        const decoded = jwtDecode(token);
        const username = decoded.sub;
        const response = await AdminBYName(username);

        if (response?.data?.id) {
          setFormData(prev => ({
            ...prev,
            adminId: response.data.id,
          }));
        } else {
          console.error("AdminId not found", response);
        }
      } catch (error) {
        alert("Error fetching adminId");
      }
    };
    fetachAdminId();
  }, []);

  return (
    <div className="container mt-4">
      <h3>Update Alert</h3>
      <form onSubmit={handleSubmit}>
        {/* Existing form fields... */}

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

        <h5>Already Assigned Resources</h5>
        {selectedResources.length > 0 ? (
          <ul className="list-group mb-3">
            {selectedResources.map(res => (
              <li key={res.id} className="list-group-item d-flex justify-content-between align-items-center">
                {res.name} ({res.type})
                <button type="button" className="btn btn-sm btn-danger" onClick={() => removeSelectedResource(res.id)}>Remove</button>
              </li>
            ))}
          </ul>
        ) : (
          <p>No resources assigned.</p>
        )}

        <h5>Add Resources to Alert</h5>
        <select className="form-control mb-2" onChange={handleResourceKindChange} value={resourceKind}>
          <option value="">Select Resource Type</option>
          <option value="PERSONNEL">PERSONNEL</option>
          <option value="TEAM">TEAM</option>
          <option value="EQUIPMENT">EQUIPMENT</option>
          <option value="SUPPLY">SUPPLY</option>
          <option value="FACILITY">FACILITY</option>
        </select>

        {availableResources.length > 0 && (
          <select className="form-control mb-2" onChange={handleResourceSelect}>
            <option value="">Select Resource</option>
            {availableResources.map(res => (
              <option key={res.id} value={res.id}>
                {res.name} - {res.city}
              </option>
            ))}
          </select>
        )}

        <button type="submit" className="btn btn-success">Update Alert</button>
      </form>
    </div>
  );
};
