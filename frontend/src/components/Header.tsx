import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'

const Header: React.FC = () => {
  return (
    <header className="fixed w-screen bg-background p-3 text-foreground">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
    </header>
  )
}

export default Header
