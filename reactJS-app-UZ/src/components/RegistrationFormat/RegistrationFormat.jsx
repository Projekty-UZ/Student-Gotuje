import React from "react";
import './RegistrationFormat.css'
const RegistrationFormat = () => {
    return (
        <div className="container">
            <div className="header">
                <div className="header-text">Zarejestruj się</div>
                <div className="header-underline"></div>
            </div>
            <div className="inputs">
                <div className="input-username-text">
                    <p>nazwa uzytkownika</p>
                    <input type="text"/>
                </div>
                <div className="input">
                    <p>hasło: </p>
                    <input type="password"/>
                </div>
            </div>

            
        </div>
        
    )
}

export default RegistrationFormat