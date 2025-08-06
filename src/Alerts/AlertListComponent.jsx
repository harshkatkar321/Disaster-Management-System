import React, { useEffect, useState } from 'react';
import { deleteAlertById, GetAdminAlerts } from './services/CreateAlertService';
import { Button, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';


export const AlertsListComponent = () => {
  const [alerts, setAlerts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchAlerts();
  }, []);

  const fetchAlerts = async () => {
    try {
      const data = await GetAdminAlerts();
      setAlerts(data);
    } catch (error) {
      console.error('Could not load alerts.');
    }
  };

//   const handleDelete = async (id) => {
//     try {
//       await deleteAlert(id);
//       fetchAlerts(); // refresh list
//     } catch (error) {
//       alert('Error deleting alert');
//     }
//   };

  const handleUpdate = (alert) => {
    // redirect or open modal
    // alert(`Update feature for alert ID: ${alert.id} coming soon.`);
    navigate("/alert/update", {
  state: {
    type: alert.type,
    message: alert.message,
    severity: alert.severity,
    location: alert.location,
    riskScore: alert.riskScore,
    region: alert.region,
    alertId: alert.id,
    description: alert.description,
    disasterId: alert.disaster.id || null , // ensure consistency
    userId: alert.userId || null,
    tags: alert.tags || [],
  }
});
  };

  
  const handleDelete = async (alertId) => {
  if (window.confirm("Are you sure you want to delete this alert?")) {
    try {
      const res = await deleteAlertById(alertId);
      alert("Alert deleted successfully!");
      navigate(0)
      // Optionally reload the list
    } catch (err) {
      console.error("Delete error:", err);
      alert("Failed to delete alert.");
      
    }
  }
};

  return (
    <div className="d-flex flex-wrap justify-content-start gap-3">
      {alerts.map((alert) => (
        <Card key={alert.id} style={{ width: '300px' }}>
          <Card.Body>
            <Card.Title>{alert.type}</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">{alert.region}</Card.Subtitle>
            <Card.Text>
              <strong>Message:</strong> {alert.message}<br />
              <strong>Severity:</strong> {alert.severity}<br />
              <strong>Location:</strong> {alert.location}<br />
              <strong>Risk Score:</strong> {alert.riskScore}
            </Card.Text>
            <div className="d-flex justify-content-between">
              <Button variant="warning" onClick={() => handleUpdate(alert)}>Update</Button>
              <Button variant="danger" onClick={() => handleDelete(alert.id)}>Delete</Button>
            </div>
          </Card.Body>
        </Card>
      ))}
    </div>
  );
};


