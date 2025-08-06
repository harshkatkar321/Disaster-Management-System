
import axios from 'axios'; // or your preferred HTTP client

const AdminProfileService = async (adminId) => {
  try {
    // 1. Get token from localStorage (same as your register flow)
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No authentication token found');
    }

    // 2. Call your backend API (adjust endpoint as needed)
    const response = await axios.get(`/api/admin/${adminId}/profile`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    // 3. Transform response data to match frontend expectations
    return {
      firstName: response.data.firstName,
      lastName: response.data.lastName,
      email: response.data.email,
      phoneNumber: response.data.phoneNumber,
      city: response.data.city,
      role: response.data.role || 'ADMIN', // Default role if not provided
      createdAt: response.data.createdAt || new Date().toISOString(),
      imageData: response.data.profileImage?.data, // Adjust based on your backend
      imageType: response.data.profileImage?.type || 'image/png'
    };

  } catch (error) {
    console.error('AdminProfileService failed:', error);
    
    // Handle specific error cases
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/admin/login'; // Force reload to clear state
    }
    
    throw new Error(error.response?.data?.message || 'Failed to fetch admin profile');
  }
};

export default AdminProfileService;