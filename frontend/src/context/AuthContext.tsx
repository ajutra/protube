import React, { createContext, useContext, useState, useEffect } from 'react'
import Cookies from 'js-cookie'
import { getEnv } from '@/utils/Env'

interface AuthContextType {
  isLoggedIn: boolean
  username: string | null
  isLoading: boolean
  login: (username: string, password: string) => Promise<{ error?: string }>
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

  const login = async (username: string, password: string) => {
    setIsLoading(true)
    try {
      const response = await fetch(`${getEnv().API_BASE_URL}/users`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      })

      if (response.ok) {
        setUsername(username)
        setIsLoggedIn(true)
        Cookies.set('username', username, { expires: 7, sameSite: 'Lax' }) // Set cookie to expire in 7 days
        return {}
      } else if (response.status === 400 || response.status === 404) {
        // Handle error event
        return { error: 'Invalid username or password' }
      } else {
        // Handle other errors
        return { error: 'An unexpected error occurred' }
      }
    } catch (error) {
      return Promise.resolve({ error: 'An unexpected error occurred' })
    } finally {
      setIsLoading(false)
    }
  }

  const logout = () => {
    setUsername(null)
    setIsLoggedIn(false)
    Cookies.remove('username')
  }

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, username, isLoading, login, logout }}
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
