import React from "react";
import './YourAccount.css'
import Navigation from "../Navigation/Navigation";

const YourAccount = () => {

    return (
        <div>
            <div className="navigation">
            <Navigation/>
            </div>
            <div className="containter">
                <p className="contact-text">Twoje konto</p>
            </div>
        </div>

    )
}
export default YourAccount;