import React from "react";
import './Navigation.css'

const Navigation = () => {
    return (
      <nav className="navbar">
        
        <ul className="navbar-links">
          <li><a href="/">Home</a></li>
          <li><a href="/about">About</a></li>
          <li><a href="/services">Services</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </nav>
    );
  };
  
  export default Navigation;