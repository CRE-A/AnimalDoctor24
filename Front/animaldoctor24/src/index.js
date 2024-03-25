import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import NotFound from "./pages/NotFound";
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App/>,
        errorElement: <NotFound />,
        children:[
            { index: true, path: '/', element: <Home /> },
            { path: '/login', element: <LoginPage /> },
        ]

    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
