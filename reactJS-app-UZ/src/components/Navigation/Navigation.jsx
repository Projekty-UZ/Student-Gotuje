import React from "react";
import './Navigation.css'

const Navigation = () => {
    return (
      <nav className="navbar">
        
        <ul className="navbar-links">
          <li><a href="/">Strona główna</a></li>
          <li><a href="/about">Przepisy dnia</a></li>
          <li><a href="/services">Services</a></li>
          <li><a href="/contact">Kontakt</a></li>
        </ul>
        <u1 className="account">
            <li><a href="/account">Twoje konto</a></li>
        </u1>
      </nav>
    );
  };
  
  export default Navigation;