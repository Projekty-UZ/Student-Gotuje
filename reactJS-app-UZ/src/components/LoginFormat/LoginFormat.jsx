import React from "react";
import './LoginFormat.css'
const LoginFormat = () => {

    const handleClick = () =>{
        console.log("hello world");
    }

    return (
        <div className="container">
            <div className="header">
                <div className="header-text">Zaloguj się</div>
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
                <button className="forget-password-button" onClick={handleClick}>Zaloguj</button> 
            </div>
            
            <div className="forget-password">
                <p>Zapomniałeś hasła?<a href="/rejestracja">Kliknij tutaj</a> </p>
                
                </div>
            <div className="submit-container">
                <div className="submit">Zaloguj</div>
                <div className="submit">Zarejestruj</div>
            </div>
        </div>
        
    )
}

export default LoginFormat