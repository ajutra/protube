import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import { ModeToggle } from './ModeToggle'
import { CircleUserRound } from 'lucide-react'

const Header: React.FC = () => {
  return (
    <header className="fixed flex w-screen items-center justify-between bg-background p-3 text-foreground">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
      <div className="float-right flex items-center space-x-5">
        <ModeToggle />
        <CircleUserRound />
      </div>
    </header>
  )
}

export default Header
