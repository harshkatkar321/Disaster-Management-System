import React, { useState } from 'react'
import { getNotVerifiedResourcesByCity, verifyResource } from './services/ResourceValidate';
import toast from 'react-hot-toast';
export const ResourceValidate = () => {

  const [city, setCity] = useState("");
  const [resources, setResources] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchResources = async () => {
    if (!city.trim()) {
      toast.error("Please enter a city");
      return;
    }
    setLoading(true);
    try {
      const data = await getNotVerifiedResourcesByCity(city);
      setResources(data);
      if (data.length === 0) {
        toast("No unverified resources found in this city");
      }
    } catch (error) {
      toast.error("Failed to fetch resources");
    } finally {
      setLoading(false);
    }
  };

  const handleVerify = async (id) => {
    try {
      await verifyResource(id);
      toast.success("Resource verified successfully");
      setResources((prev) => prev.filter((res) => res.id !== id));
    } catch (error) {
      toast.error("Failed to verify resource");
    }
  };

    return (
        <div className="container mt-4">
      <h3>Unverified Resources</h3>
      <div className="input-group mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Enter city name"
          value={city}
          onChange={(e) => setCity(e.target.value)}
        />
        <button className="btn btn-primary" onClick={fetchResources} disabled={loading}>
          {loading ? "Loading..." : "Fetch"}
        </button>
      </div>

      {resources.length > 0 && (
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>Name</th>
              <th>Type</th>
              <th>City</th>
              <th>Verified</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {resources.map((res) => (
              <tr key={res.id}>
                <td>{res.name}</td>
                <td>{res.type}</td>
                <td>{res.city}</td>
                <td>{res.verified ? "Yes" : "No"}</td>
                <td>
                  <button
                    className="btn btn-success btn-sm"
                    onClick={() => handleVerify(res.id)}
                  >
                    Verify
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
    )
}
