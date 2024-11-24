import React, { createContext, useContext, useState, useEffect } from 'react'
import Cookies from 'js-cookie'
import { getEnv } from '@/utils/Env'

interface AuthContextType {
  isLoggedIn: boolean
  username: string | null
  isLoading: boolean
  login: (username: string, password: string) => Promise<{ error?: string }>
  register: (username: string, password: string) => Promise<{ error?: string }>
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [username, setUsername] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(false)

  useEffect(() => {
    const storedUsername = Cookies.get('username')
    if (storedUsername) {
      setUsername(storedUsername)
      setIsLoggedIn(true)
    }
  }, [])

  const handleSuccessfulAuth = (username: string) => {
    setUsername(username)
    setIsLoggedIn(true)
    Cookies.set('username', username, { expires: 7, sameSite: 'Lax' }) // Set cookie to expire in 7 days
  }

  const authenticate = async (
    url: string,
    username: string,
    password: string
  ) => {
    setIsLoading(true)
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    })

    setIsLoading(false)

    if (response.ok) {
      handleSuccessfulAuth(username)
      return {}
    } else {
      const errorText = await response.text()
      return { error: errorText }
    }
  }

  const login = (username: string, password: string) => {
    return authenticate(`${getEnv().API_LOGIN_URL}`, username, password)
  }

  const register = (username: string, password: string) => {
    return authenticate(`${getEnv().API_REGISTER_URL}`, username, password)
  }

  const logout = () => {
    setUsername(null)
    setIsLoggedIn(false)
    Cookies.remove('username')
  }

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, username, isLoading, login, register, logout }}
    >
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
