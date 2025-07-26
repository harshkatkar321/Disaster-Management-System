import React from 'react'
import { Navbar,Container,Nav } from 'react-bootstrap'
import { Link } from 'react-router-dom'

export const NavigationBar = () => {

  
    return (
        <>
             <Navbar bg="info" data-bs-theme="light">
        <Container>
          <Navbar.Brand as={Link} to="/">DMS</Navbar.Brand>
          <Navbar.Toggle aria-controls='basic-navbar-nav' />
          <Navbar.Collapse id='basic-navbar-nav' />
          <Nav className="me-auto">
          { role === 'USER' && (
            <>
            <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
            <Nav.Link as={Link} to="/report">ReportDisaster</Nav.Link>
            </>
          ) }
        
          { role === 'ADMIN' && (
            <>
            <Nav.Link as={Link} to="/users">Manage users</Nav.Link>
            <Nav.Link as={Link} to="/disasters">Manage Disasters</Nav.Link>
            </>
          ) }

            { role === 'SUPERADMIN' && (
            <>
            <Nav.Link as={Link} to="/SYSTEM">System Overview</Nav.Link>
            <Nav.Link as={Link} to="/admins">Manage Admins</Nav.Link>
            </>
          ) }

          

            <Nav.Link as={Link} to="/about">About</Nav.Link>
            <Nav.Link as={Link} to="/login">Login</Nav.Link>
            <Nav.Link as={Link} to="/logout">Logout</Nav.Link>
          
          </Nav>
        </Container>
      </Navbar>
        </>
    )
}
