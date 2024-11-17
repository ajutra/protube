import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import ProfileLogo from '../assets/profileLogo.svg'

const Header: React.FC = () => {
  return (
    <header className="fixed w-screen bg-background p-3 text-foreground">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
      <img
        src={ProfileLogo}
        alt="Profile Logo"
        className="float-right h-10 w-10 stroke-primary-foreground"
      />
    </header>
  )
}

export default Header
