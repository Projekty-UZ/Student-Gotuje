import React from "react";
import './LoginFormat.css'
const LoginFormat = () => {
    return (
        <div className="container">
            <div className="header">
                <div className="text">Sign Up</div>
                <div className="underline"></div>
            </div>
            <div className="inputs">
                <div className="input">
                    <p>nazwa uzytkownika</p>
                    <input type="text"/>
                </div>
                <div className="input">
                    <input type="username"/>
                </div>
                <div className="input">
                    <input type="password"/>
                </div>
            </div>

            <div className="forget-password">Zapomniales hasla? <span>Kliknij tu</span></div>
            <div className="submit-container">
                <div className="submit">Zaloguj</div>
                <div className="submit">Zarejestruj</div>
            </div>
        </div>
        
    )
}

export default LoginFormat