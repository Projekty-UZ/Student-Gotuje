import React from "react";
import './Navigation.css'

const Navigation = () => {
    return (
      <nav className="navbar">
        
        <ul className="navbar-links">
          <li><a href="/">Strona główna</a></li>
          <li><a href="/przepis-dnia">Przepisy dnia</a></li>
          <li><a href="/przepisy">Wszystkie przepisy</a></li>
          <li><a href="/kontakt">Kontakt</a></li>
        </ul>
        <u1 className="account">
            <li><a href="/twoje-konto">Twoje konto</a></li>
        </u1>
      </nav>
    );
  };
  
  export default Navigation;