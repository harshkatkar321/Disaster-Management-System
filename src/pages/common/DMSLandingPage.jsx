import React from 'react'
import { Navbar,Nav,Container } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { Footer } from '../../assets/Footer'

export const DMSLandingPage = () => {
    return (
        <>
           <Navbar bg="info" data-bs-theme="light">
        <Container>
          <Navbar.Brand as={Link} to="/">DMS</Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/register">Register</Nav.Link>
            <Nav.Link as={Link} to="/login">Login</Nav.Link>
            <Nav.Link as={Link} to="/about">About</Nav.Link>
          </Nav>
        </Container>
      </Navbar>

      {/* Hero Section */}
        <section className='text-center text-white bg-dark py-5' >
        <div className="container">
            <h1 className='display-4 fw-bold' >Disaster Management System</h1>
             <p className="lead mt-3">Enabling quick response, efficient coordination, and disaster relief.</p>
          <div className="mt-4">
            <a className="btn btn-outline-light btn-lg mx-2" href="/login">Report Disaster</a>
            <a className="btn btn-outline-warning btn-lg mx-2" href="/register">Join Rescue Network</a>
          </div>
        </div>
        </section>

        {/* About / Info Section */}
      <section className="py-5 bg-light">
        <div className="container text-center">
          <h2>What is DMS?</h2>
          <p className="mt-3">
            The Disaster Management System (DMS) is a centralized platform designed to report, manage,
            and monitor disasters like floods, earthquakes, storms, and accidents in real-time.
            It connects users, responders, and administrators for faster decision-making.
          </p>
        </div>
      </section>

      {/* Emergency Tips Section */}
      <section className="bg-warning-subtle py-5">
        <div className="container text-center">
          <h3 className="mb-4">Emergency Tips</h3>
          <div className="row">
            <div className="col-md-4">
              <h5>Stay Calm</h5>
              <p>Dont panic—follow verified alerts and stay updated.</p>
            </div>
            <div className="col-md-4">
              <h5>Report Incidents</h5>
              <p>Use the platform to report emergencies with accurate details.</p>
            </div>
            <div className="col-md-4">
              <h5>Stay Connected</h5>
              <p>Keep your mobile charged and follow official instructions.</p>
            </div>
          </div>
        </div>
      </section>

      <Footer/>
    

        </>
    )
}
