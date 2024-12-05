import React from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '@/context/AuthContext'
import { AppRoutes } from '@/enums/AppRoutes'
import { ModeToggle } from '@/components/ModeToggle'
import { ProfileButton } from '@/components/ProfileButton'
import { LoginButton } from '@/components/LoginButton'
import SearchBar from '@/components/SearchBar'
import logoLightMode from '@/assets/logoLightMode.png'
import logoDarkMode from '@/assets/logoDarkMode.png'

const Header: React.FC = () => {
  const { isLoggedIn } = useAuth()

  return (
    <div className="fixed z-50 flex w-screen items-center justify-between rounded-b-xl bg-background pb-2 pl-6 pr-8 pt-6 text-foreground">
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
      <div className="relative w-2/6">
        <SearchBar />
      </div>
      <div className="float-right flex items-center space-x-5">
        <ModeToggle />
        {isLoggedIn ? <ProfileButton /> : <LoginButton />}
      </div>
    </div>
  )
}

export default Header
