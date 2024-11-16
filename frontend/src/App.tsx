import React from 'react'
import { Routes, Route, BrowserRouter } from 'react-router-dom'
import { AppRoutes } from './enums/AppRoutes'
import Home from './pages/Home'
import { ThemeProvider } from './components/themeProvider'

const App: React.FC = () => {
    return (
        <BrowserRouter>
            <ThemeProvider defaultTheme="system" storageKey="vite-ui-theme">
                <Routes>
                    <Route path={AppRoutes.HOME} element={<Home />} />
                </Routes>
            </ThemeProvider>
        </BrowserRouter>
    )
}

export default App
