import { render, screen } from '@testing-library/react'
import '@testing-library/jest-dom'
import { BrowserRouter } from 'react-router-dom'
import Header from './Header'
import { AuthProvider } from '@/context/AuthContext'

describe('Header Component', () => {
  test('renders the logo', () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <Header />
        </AuthProvider>
      </BrowserRouter>
    )

    const logoLightElement = screen.getByAltText('Logo Light')
    expect(logoLightElement).toBeInTheDocument()

    const logoDarkElement = screen.getByAltText('Logo Dark')
    expect(logoDarkElement).toBeInTheDocument()
  })

  test('header has correct class names and styles', () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <Header />
        </AuthProvider>
      </BrowserRouter>
    )

    const headerElement = screen.getByRole('banner')
    expect(headerElement).toHaveClass(
      'fixed z-50 w-full bg-background p-4 text-foreground'
    )
  })
})
