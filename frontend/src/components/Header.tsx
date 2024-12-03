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
    <header className="fixed flex w-screen items-center justify-between rounded-b-xl bg-background p-8 text-foreground">
      <Link to={AppRoutes.HOME} className="flex items-center text-start">
        <img
          src={logoLight}
          alt="Logo Light"
          className="hidden h-12 dark:block md:h-20"
          style={{ maxHeight: '4rem', height: 'auto' }}
        />
        <img
          src={logoDark}
          alt="Logo Dark"
          className="block h-12 dark:hidden md:h-20"
          style={{ maxHeight: '4rem', height: 'auto' }}
        />
      </Link>
      <div className="float-right flex items-center space-x-5">
        <ModeToggle />
        {isLoggedIn ? <ProfileButton /> : <LoginButton />}
      </div>
    </header>
  )
}

export default Header
