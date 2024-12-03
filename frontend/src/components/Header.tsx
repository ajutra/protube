import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { AppRoutes } from '../enums/AppRoutes'
import { ModeToggle } from './ModeToggle'
import { LoginButton } from './LoginButton'
import { useAuth } from '@/context/AuthContext'
import { ProfileButton } from './ProfileButton'
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from '@/components/ui/command'

const Header: React.FC = () => {
  const { isLoggedIn } = useAuth()
  const [isSearching, setIsSearching] = useState(false)

  return (
    <header className="fixed z-50 flex w-screen items-center justify-between rounded-b-xl bg-background p-8 text-foreground">
      <Link to={AppRoutes.HOME} className="text-start text-4xl font-bold">
        Protube
      </Link>
      <div className="relative">
        <Command className="rounded-lg border focus-within:border-primary md:min-w-[450px]">
          <CommandInput
            placeholder="Search"
            onFocus={() => setIsSearching(true)}
            onBlur={() => setIsSearching(false)}
          />
          {isSearching && (
            <CommandList className="absolute left-0 right-0 top-full mt-1 overflow-y-auto rounded-xl bg-background">
              <CommandEmpty>No results found.</CommandEmpty>
              <CommandGroup heading="Suggestions">
                <CommandItem>
                  <span>Calendar</span>
                </CommandItem>
                <CommandItem>
                  <span>Search Emoji</span>
                </CommandItem>
              </CommandGroup>
            </CommandList>
          )}
        </Command>
      </div>
      <div className="float-right flex items-center space-x-5">
        <ModeToggle />
        {isLoggedIn ? <ProfileButton /> : <LoginButton />}
      </div>
    </header>
  )
}

export default Header
