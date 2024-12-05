import React from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import { ModeToggle } from './ModeToggle'
import { LoginButton } from './LoginButton'
import { useAuth } from '@/context/AuthContext'
import { ProfileButton } from './ProfileButton'
import logoLight from '../assets/logoLight.png'
import logoDark from '../assets/logoDark.png'

const Header: React.FC = () => {
  const { isLoggedIn } = useAuth()

  return (
    <header className="fixed z-50 w-full bg-background p-4 text-foreground">
      <div className="flex items-center justify-between pl-4 pr-4">
        <Link to={AppRoutes.HOME} className="flex items-center text-start">
          <img
            src={logoLight}
            alt="Logo Light"
            className="hidden h-16 dark:block md:h-24"
            style={{ height: 'auto', maxHeight: '3.5rem' }}
          />
          <img
            src={logoDark}
            alt="Logo Dark"
            className="block h-16 dark:hidden md:h-24"
            style={{ height: 'auto', maxHeight: '3.5rem' }}
          />
        </Link>
        <div className="flex items-center space-x-5">
          <ModeToggle />
          {isLoggedIn ? <ProfileButton /> : <LoginButton />}
        </div>
      </div>
    </header>
  )
}

export default Header
