import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import Register from './AuthPages/Register'
import { NavbarStatic } from './assets/NavigationBar'
import { Login } from './AuthPages/Login'
import { Route,Routes } from 'react-router-dom'
import { Home } from './pages/user/UserHome'
import { Update } from './pages/user/Update'
import { DMSLandingPage } from './pages/common/DMSLandingPage'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      {/* <DMSLandingPage/> */}
      <Routes>

        <Route path="/" element={<DMSLandingPage/>} />
        <Route path="/home" element={<Home/>} />
        <Route path="/register" element={<Register/>} />
        <Route path="/login" element={<Login/>} />
        <Route path="/update" element={<Update/>} />
      </Routes>
    </>
  )
}

export default App
