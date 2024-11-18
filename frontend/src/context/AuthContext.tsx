import React, { createContext, useContext, useState, useEffect } from 'react'
import Cookies from 'js-cookie'

interface AuthContextType {
  isLoggedIn: boolean
  username: string | null
  login: (username: string) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [username, setUsername] = useState<string | null>(null)

  useEffect(() => {
    const storedUsername = Cookies.get('username')
    if (storedUsername) {
      setUsername(storedUsername)
      setIsLoggedIn(true)
    }
  }, [])

  const login = (username: string) => {
    setUsername(username)
    setIsLoggedIn(true)
    Cookies.set('username', username, { expires: 7, sameSite: 'Lax' }) // Set cookie to expire in 7 days
  }

  const logout = () => {
    setUsername(null)
    setIsLoggedIn(false)
    Cookies.remove('username')
  }

  return (
    <AuthContext.Provider value={{ isLoggedIn, username, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}
