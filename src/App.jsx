import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import Register from './AuthPages/Register'
import { NavbarStatic } from './components/NavbarStatic'
import { Login } from './AuthPages/Login'
import { Route,Routes } from 'react-router-dom'
import { Home } from './Home'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <NavbarStatic/>
      <Routes>

        <Route path="/" element={<Home/>} />
        <Route path="/register" element={<Register/>} />
        <Route path="/login" element={<Login/>} />
      </Routes>
    </>
  )
}

export default App
