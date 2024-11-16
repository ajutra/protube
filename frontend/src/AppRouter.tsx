import React from "react";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { AppRoutes } from "./enums/AppRoutes";
import App from "./App";
import Profile from "./components/Profile";

const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={AppRoutes.HOME} element={<App />} />
        <Route path={AppRoutes.PROFILE} element={<Profile username={"nil"}/>} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
