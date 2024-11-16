import React from "react";
import { useNavigate } from "react-router-dom";
import { AppRoutes } from "../enums/AppRoutes";
import profileLogo from "../assets/profile.svg";

const Header: React.FC = () => {
  const navigate = useNavigate();

  const handleTitleClick = () => {
    navigate(AppRoutes.HOME);
  };

  const handleProfileClick = () => {
    navigate(AppRoutes.PROFILE);
  };

  return (
    <header className="bg-dark text-light p-3 w-100 fixed-top d-flex justify-content-between align-items-center">
      <h1
        className="text-start m-0"
        onClick={handleTitleClick}
        style={{ cursor: "pointer" }}
      >
        Protube
      </h1>
      <div onClick={handleProfileClick} style={{ cursor: "pointer" }}>
        <img
          src={profileLogo}
          alt="profile"
        />
      </div>
    </header>
  );
};

export default Header;
