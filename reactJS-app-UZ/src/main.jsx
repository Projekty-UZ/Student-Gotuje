import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import LoginFormat from './components/LoginFormat/LoginFormat.jsx'
import RegistrationFormat from './components/RegistrationFormat/RegistrationFormat.jsx'
import RecipeOfTheDay from './components/RecipeOfTheDay/RecipeOfTheDay.jsx'
import Recipes from './components/Recipes/Recipes.jsx'
import Contact from './components/Contact/Contact.jsx'

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
    element: <Recipes/>
  },
  {
    path: "kontakt",
    element: <Contact/>
  }
]);



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router = {router} />
  </StrictMode>,
)
