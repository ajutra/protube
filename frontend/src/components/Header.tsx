import React from "react";
import { useNavigate } from "react-router-dom";
import { AppRoutes } from "../enums/AppRoutes";

const Header: React.FC = () => {
  const navigate = useNavigate();

  const handleTitleClick = () => {
    navigate(AppRoutes.HOME);
  };

  return (
    <header className="bg-dark text-light p-3 w-100 fixed-top">
      <h1
        className="text-start"
        onClick={handleTitleClick}
        style={{ cursor: "pointer" }}
      >
        Protube
      </h1>
    </header>
  );
};

export default Header;
