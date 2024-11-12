import React from "react";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { AppRoutes } from "./enums/AppRoutes";
import App from "./App";

const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={AppRoutes.HOME} element={<App />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
