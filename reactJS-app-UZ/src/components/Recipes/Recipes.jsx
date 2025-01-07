import React from "react";
import './Recipes.css'
import Navigation from "../Navigation/Navigation";

const Recipes = () => {
    return (
        <div>
            <div className="navigation">
            <Navigation/>
            </div>
            <div className="containter">
                <p className="recipes-text">wszystkie przepisy</p>
            </div>
        </div>

    )
}
export default Recipes;