import React from "react";
import './RecipesPage.css'
import Navigation from "../Navigation/Navigation";

const RecipesPage = () => {
    return (
        <div>
            <div className="navigation">
            <Navigation/>
            </div>
            <div className="containter">
                <a href="/przepis"><button className="recipes-button">Przepis na naleśniki</button></a>
            </div>
        </div>

    )
}
export default RecipesPage;