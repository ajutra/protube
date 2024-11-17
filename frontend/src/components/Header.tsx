import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import { ModeToggle } from './ModeToggle'
import { LoginButton } from './LoginButton'

const Header: React.FC = () => {
  return (
    <header className="fixed flex w-screen items-center justify-between rounded-b-xl bg-background p-4 text-foreground">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
      <div className="float-right flex items-center space-x-5">
        <ModeToggle />
        <LoginButton />
      </div>
    </header>
  )
}

export default Header
