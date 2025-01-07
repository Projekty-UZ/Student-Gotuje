import React from "react";
import './MainPage.css'
const MainPage = () => {

    return (
        <div>
            
            <p className="hewlo">Hello World</p>

            <div className="containter">
        
        <div className="buttons">
           <a href="/rejestracja" className="button-register"><button>Rejestracja</button></a> 
           <a href="/logowanie" className="button-login"><button>Logowanie</button></a> 

        </div>


    </div>
        </div>

    )
}

export default MainPage