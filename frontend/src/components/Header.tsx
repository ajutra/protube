import React from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '@/context/AuthContext'
import { AppRoutes } from '@/enums/AppRoutes'
import { ModeToggle } from '@/components/ModeToggle'
import { ProfileButton } from '@/components/ProfileButton'
import { LoginButton } from '@/components/LoginButton'
import SearchBar from '@/components/SearchBar'

const Header: React.FC = () => {
  const { isLoggedIn } = useAuth()

  return (
    <div className="fixed z-50 flex w-screen items-center justify-between rounded-b-xl bg-background p-8 text-foreground">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
      <div className="relative w-2/4">
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
