import React, { useEffect, useState } from 'react'
import { updateUserProfile } from './services/UpdateUserProfile';
import { UserProfile } from '../User/services/UserProfile';
import { jwtDecode } from 'jwt-decode';
import { Form, Button, Container, Row, Col, Card } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';

export const UpdateUser = () => {
    const navigate = useNavigate();
    const [userData, setUserData] = useState({
        city:"",
        phoneNumber:"",
        password:""
    });
    const [imageFile, setImageFile] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);
    const [userId, setUserId] = useState("");

    useEffect(() =>{
        const token = localStorage.getItem("token");
        if(token){
            const decoded = jwtDecode(token);
            const username =decoded.sub;
            fetchUserData(username);
        }
    },[]);

    const fetchUserData  = async (username) =>{
        try{
            const data = await UserProfile(username);
            setUserId(data.id);
            setUserData({
                city : data.city || "",
                phoneNumber : data.phoneNumber || "",
                password : "",
            });
            if(data.imageData){
                const base64Img = `data:${data.imageType};base64,${data.imageData}`;
                setImagePreview(base64Img);
            }
        } catch(error){
            console.error("Error fetching user data",error);
        }
    };

    const handleChange = (e) =>{
        setUserData({ ...userData,[e.target.name]:e.target.value});
    };

    const handleImageChange = (e)=>{
        const file = e.target.files[0];
        setImageFile(file);
        if(file){
            const reader = new FileReader();
            reader.onloadend = () => setImagePreview(reader.result);
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            const res = await updateUserProfile(userId,userData,imageFile);
            alert("User data updated successfully");
            navigate("/user/home");
        }
        catch(error){
            console.error("Put error ",error);
            alert("update failed")
            navigate("/user/update");
            
        }
    };

    return (
        <>
          <Container className="my-5">
      <Card className="shadow-lg p-4">
        <h3 className="mb-4">Update Profile</h3>
        <Form onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Col md={4} className="text-center">
              {imagePreview ? (
                <img
                  src={imagePreview}
                  alt="Profile Preview"
                  className="rounded-circle"
                  style={{ width: "150px", height: "150px", objectFit: "cover", border: "2px solid #ccc" }}
                />
              ) : (
                <div
                  style={{
                    width: "150px",
                    height: "150px",
                    borderRadius: "50%",
                    background: "#eee",
                    lineHeight: "150px",
                    textAlign: "center",
                    border: "2px solid #ccc",
                  }}
                >
                  No Image
                </div>
              )}
              <Form.Group className="mt-3">
                <Form.Label>Change Profile Picture</Form.Label>
                <Form.Control type="file" accept="image/*" onChange={handleImageChange} />
              </Form.Group>
            </Col>

            <Col md={8}>
              <Form.Group className="mb-3">
                <Form.Label>City</Form.Label>
                <Form.Control
                  type="text"
                  name="city"
                  value={userData.city}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Phone Number</Form.Label>
                <Form.Control
                  type="text"
                  name="phoneNumber"
                  value={userData.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>New Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  value={userData.password}
                  onChange={handleChange}
                  required
                />
              </Form.Group>

              <Button variant="primary" type="submit">
                Update Profile
              </Button>
            </Col>
          </Row>
        </Form>
      </Card>
    </Container>  
        </>
    )
}
