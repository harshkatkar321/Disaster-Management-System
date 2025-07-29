import React, { useEffect, useState, useRef } from 'react'
import { DisasterList } from './services/DisasterList';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import './css/listDisaster.css';

export const ListDisaster = () => {

    const mapRef = useRef();

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

    const defaultIcon = new L.Icon({
        iconUrl: 'https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
    });

    return (
        <div>
           <h2>Disaster List</h2>
           {disasters.map((disaster,index) => (
            <div key={index} style={{ marginBottom: '40px', border: '1px solid gray', padding: '10px'  }} >
                <h4>{disaster.type}</h4>
                <p><strong>Location : </strong> {disaster.location} </p>
                <p><strong>Description : </strong> {disaster.description} </p>
                <p><strong>Status : </strong> {disaster.status} </p>

            {/* Image Display  */}

           <img src={convertToImageSrc(disaster.imageData,disaster.imageType)} 
             alt='Disaster' 
                           style={{ width: '300px', height: '200px', objectFit: 'cover', marginBottom: '10px' }} 
              />
            
            {/* Map Diaplay  */}

            {disaster.mapLocation?.coordinates && (
                <div className="map-box">
  <MapContainer
    center={[18.51283394, 73.81054636]}
    zoom={13}
    style={{ height: '100%', width: '100%' }}
    whenCreated={(mapInstance) => {
      mapRef.current = mapInstance;
    }}
  >
    <TileLayer
      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      attribution='&copy; OpenStreetMap contributors'
    />
    <Marker position={[18.51283394, 73.81054636]}>
      <Popup>Disaster Location</Popup>
    </Marker>
  </MapContainer>
</div>

               
            )}

            </div>
           ))}
        </div>
    )
}
