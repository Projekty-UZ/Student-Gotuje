import React from "react";
import './MainPage.css'
const MainPage = () => {

    const handleClick = () =>{
        console.log("hello world");
    }

    return (<div className="containter">
        <p>Hello World</p>
        <div className="buttons">
           <a href="/rejestracja" className="button-register"><button>Rejestracja</button></a> 
           <a href="/logowanie" className="button-login"><button>Logowanie</button></a> 

        </div>


    </div>

    )
}

export default MainPage