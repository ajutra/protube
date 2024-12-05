import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import { ModeToggle } from './ModeToggle'
import { LoginButton } from './LoginButton'
import { useAuth } from '@/context/AuthContext'
import { ProfileButton } from './ProfileButton'
import logoLightMode from '@/assets/logoLightMode.png'
import logoDarkMode from '@/assets/logoDarkMode.png'

const Header: React.FC = () => {
  const { isLoggedIn } = useAuth()

  return (
    <header className="fixed z-50 flex w-screen items-center justify-between rounded-b-xl bg-background pb-2 pl-6 pr-8 pt-6 text-foreground">
      <Link to={AppRoutes.HOME} className="flex items-center">
        <img
          src={logoDarkMode}
          alt="Protube Logo"
          className="hidden w-40 dark:block"
        />
        <img
          src={logoLightMode}
          alt="Protube Logo"
          className="w-40 dark:hidden"
        />
      </Link>
      <div className="flex items-center space-x-5">
        <ModeToggle />
        {isLoggedIn ? <ProfileButton /> : <LoginButton />}
      </div>
    </header>
  )
}

export default Header
