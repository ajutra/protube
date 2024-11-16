import React from 'react'
import { Routes, Route, BrowserRouter } from 'react-router-dom'
import { AppRoutes } from './enums/AppRoutes'
import Home from './pages/Home'

const App: React.FC = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={AppRoutes.HOME} element={<Home />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
