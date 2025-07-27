import { Routes, Route } from 'react-router-dom';
import { Navbar } from './Navigation/Components/Navbar';
import { Footer } from './Navigation/Components/Footer';
import { Home } from './Home/Home';
import { Login } from './Common/Components/Login.jsx';
import Register from './Common/Components/Register';
import { HomeSA } from './SuperAdmin/Components/HomeSA.jsx';

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
      </Routes>
      <Footer />
    </>
  );
};
