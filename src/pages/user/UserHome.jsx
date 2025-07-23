import React from 'react'
import { NavbarStatic } from '../../assets/NavigationBar'
import { Footer } from '../../assets/Footer'

export const Home = () => {
    return (
        <div>
            <NavbarStatic/>
            <h1>Welcome to home page</h1>
            <Footer/>
        </div>
    )
}
