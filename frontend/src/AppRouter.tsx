import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import VideoDetails from "./components/VideoDetails";
import { AppRoutes } from "./enums/AppRoutes";

const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path={AppRoutes.VIDEO_DETAILS} element={<VideoDetails />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
