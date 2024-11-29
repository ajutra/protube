import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import { ModeToggle } from './ModeToggle'
import { LoginButton } from './LoginButton'
import { useAuth } from '@/context/AuthContext'
import { ProfileButton } from './ProfileButton'

const Header: React.FC = () => {
  const { isLoggedIn } = useAuth()

  return (
    <header className="fixed top-0 z-50 flex w-full items-center justify-between rounded-b-xl bg-background p-8 text-foreground shadow">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
      <div className="float-right flex items-center space-x-5">
        <ModeToggle />
        {isLoggedIn ? <ProfileButton /> : <LoginButton />}
      </div>
    </header>
  )
}

export default Header
