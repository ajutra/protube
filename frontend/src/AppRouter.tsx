import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import VideoDetails from "./components/VideoDetails";
import { AppRoutes } from "./enums/AppRoutes";

const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={AppRoutes.VIDEO_DETAILS} element={<VideoDetails />} />
        <Route path={AppRoutes.HOME} element={<App />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
