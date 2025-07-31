import React, { useEffect, useState, useRef } from 'react'
import { DisasterList } from './services/DisasterList';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import './css/listDisaster.css';
import { useNavigate } from 'react-router-dom';

export const ListDisaster = () => {

    const mapRef = useRef();
    const navigate = useNavigate();

    const [disasters, setDisasters] = useState([]);

    useEffect(() => {
  if (mapRef.current) {
    setTimeout(() => {
      mapRef.current.invalidateSize();
    }, 200); // 200ms delay ensures full render before recalculation
  }
}, [disasters]); // or any trigger that causes map render


    useEffect(() => {
  const map = mapRef.current;
  if (map) {
    map.dragging.disable();
    map.touchZoom.disable();
    map.doubleClickZoom.disable();
    map.scrollWheelZoom.disable();
    map.boxZoom.disable();
    map.keyboard.disable();
    if (map.tap) map.tap.disable();
  }
}, []);



    useEffect(() => {
        const fetchDisasters = async () => {
            try{
                const data = await DisasterList();
                setDisasters(data);
                console.log("disaster list",data);
            }
            catch(error){
                console.error("Listing failed",error);
                
            }
        };
        fetchDisasters();
    },[]);

    const convertToImageSrc = (imageData,imageType) => {
        return `data:${imageType};base64,${imageData}`;
    };

    const handleCreateAlert = (disaster) => {
  navigate(`/admin/create-alert`,{
    state : {
      type: disaster.type,
      location: disaster.location,
      description: disaster.description,
      disasterId: disaster.id,
      userid : disaster.user_id,
      region : alert.region,
      
    }
  });
};

    const defaultIcon = new L.Icon({
        iconUrl: 'https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
    });

    return (
<>

  <div style={{ padding: '20px' }}>
    <h2>Disaster List</h2>
    <table className="table table-bordered table-striped" style={{ width: '100%', textAlign: 'center' }}>
      <thead className="thead-dark">
        <tr>
          <th>Type</th>
          <th>Location</th>
          <th>Description</th>
          <th>Status</th>
          <th>Image</th>
          <th>Map</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        {disasters.map((disaster, index) => (
          <tr key={index}>
            <td>{disaster.type}</td>
            <td>{disaster.location}</td>
            <td>{disaster.description}</td>
            <td>{disaster.status}</td>
            <td>
              <img
                src={convertToImageSrc(disaster.imageData, disaster.imageType)}
                alt="Disaster"
                style={{ width: '150px', height: '100px', objectFit: 'cover' }}
              />
            </td>
           <td style={{ minWidth: '220px' }}>
  <div
    style={{
      width: '200px',
      height: '150px',
      overflow: 'hidden',
      borderRadius: '8px',
      border: '1px solid #ccc',
      margin: '0 auto',
    }}
  >
    {disaster.mapLocation?.coordinates && (
      <MapContainer
        center={[18.51283394, 73.81054636]}
        zoom={13}
        style={{ width: '100%', height: '100%' }}
        scrollWheelZoom={false}
        dragging={false}
        zoomControl={false}
        doubleClickZoom={false}
        attributionControl={false}
      >
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Marker position={[18.51283394, 73.81054636]}>
          <Popup>Disaster Location</Popup>
        </Marker>
      </MapContainer>
    )}
  </div>
</td>

            <td>
              <button
                className="btn btn-warning"
                onClick={() => handleCreateAlert(disaster)}
              >
                Create Alert
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

</>
    )
}
