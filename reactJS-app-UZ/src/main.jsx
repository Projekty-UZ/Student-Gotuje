import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import LoginFormat from './components/LoginFormat/LoginFormat.jsx'
import RegistrationFormat from './components/RegistrationFormat/RegistrationFormat.jsx'
import RecipeOfTheDay from './components/RecipeOfTheDay/RecipeOfTheDay.jsx'
import RecipesPage from './components/RecipesPage/RecipesPage.jsx'
import Contact from './components/Contact/Contact.jsx'
import SingleRecipe from './components/SingleRecipe/SingleRecipe.jsx'
import YourAccount from './components/YourAccount/YourAccount.jsx'

import App from './App.jsx'
import {
  createBrowserRouter,
  RouterProvider,
  Route,
} from "react-router-dom";


const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
  },
  {
    path: "logowanie",
    element: <LoginFormat/>
  },
  {
    path: "rejestracja",
    element: <RegistrationFormat/>
  },
  {
    path: "przepis-dnia",
    element: <RecipeOfTheDay/>
  },
  {
    path: "przepisy",
    element: <RecipesPage/>
  },
  {
    path: "kontakt",
    element: <Contact/>
  },
  {
    path: "przepis",
    element: <SingleRecipe/>
  },
  {
    path: "twoje-konto",
    element: <YourAccount/>
  }
]);



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router = {router} />
  </StrictMode>,
)
