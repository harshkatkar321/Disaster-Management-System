import { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import Register from './common/components/Register'
import { Route,Routes } from 'react-router-dom'
import { Home } from './user/UserHome'
import { Update } from './user/Update'
import { DMSLandingPage } from './DMSLandingPage'
import { About } from './common/About'
import { Login } from './common/components/Login'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      
      <Routes>

        <Route path="/" element={<DMSLandingPage/>} />
        <Route path="/home" element={<Home/>} />
        <Route path="/register" element={<Register/>} />
        <Route path="/login" element={<Login/>} />
        <Route path="/update" element={<Update/>} />
        <Route path="/about" element={<About/>} />
       
      </Routes>
    </>
  )
}

export default App
