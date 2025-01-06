import React from "react";
import './LoginFormat.css';
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
                <p>Zapomniałeś hasła?<br/><a href="/rejestracja">Kliknij tutaj<br/></a> </p>
            </div>
            <div className="create-account">
                <p><a href="/rejestracja">Utwórz nowe konto</a></p>
            </div>
            
        </div>
        
    )
}

export default LoginFormat