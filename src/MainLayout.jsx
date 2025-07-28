import { Routes, Route } from 'react-router-dom';
import { Navbar } from './Navigation/Components/Navbar';
import { Footer } from './Navigation/Components/Footer';
import { Home } from './Home/Home';
import { Login } from './Common/Components/Login.jsx';
import Register from './Common/Components/Register';
import { HomeSA } from './SuperAdmin/Components/HomeSA.jsx';
import { UserHome } from './User/Home.jsx';
import { CreateDisaster } from './Disaster/ReportDisaster.jsx';
import AdminRegister from './Admin/AdminRegister.jsx';
import { UserProfilePage } from './User/UserProfile.jsx';
import { UpdateUser } from './User/UpdateUser.jsx';
import { AdminHome } from './Admin/AdminHome.jsx';
import { AlertCreate } from './Alerts/AlertCreate.jsx';
import { ListDisaster } from './Disaster/ListDisaster.jsx';


export const MainLayout = () => {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/super-admin/home" element={<HomeSA />} />
        <Route path="/super-admin/admin/register" element={<AdminRegister/>} />
        <Route path="/super-admin/admin/register" element={<AdminRegister/>} />
        <Route path="/admin/home" element={<AdminHome/>} />
        <Route path="/admin/alert/create" element={<AlertCreate/>} />
        <Route path="/disaster/list" element={<ListDisaster/>} />
        <Route path="/user/home" element={<UserHome />} />
        <Route path="/user/profile" element={<UserProfilePage />} />
        <Route path="/user/update" element={<UpdateUser />} />
        <Route path="/user/reportdisaster" element={<CreateDisaster/>} />
      </Routes>
      <Footer />
    </>
  );
};
