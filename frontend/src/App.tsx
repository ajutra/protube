import React from 'react'
import { Routes, Route, BrowserRouter } from 'react-router-dom'
import { AppRoutes } from './enums/AppRoutes'
import Home from './pages/Home'
import { ThemeProvider } from './components/themeProvider'
import Layout from './pages/Layout'
import { AuthProvider } from '@/context/AuthContext'
import Profile from './pages/Profile'

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <ThemeProvider defaultTheme="system" storageKey="vite-ui-theme">
        <AuthProvider>
          <Layout>
            <Routes>
              <Route path={AppRoutes.HOME} element={<Home />} />
              <Route path={AppRoutes.PROFILE} element={<Profile />} />
            </Routes>
          </Layout>
        </AuthProvider>
      </ThemeProvider>
    </BrowserRouter>
  )
}

export default App
