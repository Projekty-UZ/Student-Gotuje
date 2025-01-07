import React from "react";
import './RecipeOfTheDay.css'
import Navigation from "../Navigation/Navigation";
const RecipeOfTheDay = () => {

    return (
        <div>
            <div className="navigation">
                    <Navigation/>
                </div>
            <div className="containter">
                <p className="welcome-text">Przepisy dnia</p>
                
                <div className="asdf">
                    <p>1</p>
                </div>
               
                

            </div>
        </div>

    )
}

export default RecipeOfTheDay