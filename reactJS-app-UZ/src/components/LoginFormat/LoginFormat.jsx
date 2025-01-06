import React, { useState } from "react";
import './LoginFormat.css';
import { Route } from "react-router-dom";
import RegistrationFormat from "../RegistrationFormat/RegistrationFormat";
const LoginFormat = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleClick = () =>{
        if(username!='' && password!="") {
            if(username == "admin" && password=="admin") {
                <Route path="../RegistrationFormat/RegistrationFormat" Component={RegistrationFormat} />
            }
            else {
                console.log("zle dane")
            }
        }
        else {
            console.log("Brak danych");
        }
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
                    <input type="text" required value={username} onChange={(e) => setUsername(e.target.value)}/>
                </div>
                <div className="input">
                    <p>hasło: </p>
                    <input type="password" required value={password} onChange={(e) => setPassword(e.target.value)}/>
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