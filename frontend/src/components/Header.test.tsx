import { render, screen } from '@testing-library/react'
import '@testing-library/jest-dom'
import { BrowserRouter } from 'react-router-dom'
import Header from './Header'
import { AuthProvider } from '@/context/AuthContext'

describe('Header Component', () => {
  test('renders the title', () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <Header />
        </AuthProvider>
      </BrowserRouter>
    )

    const titleElement = screen.getByText('Protube')
    expect(titleElement).toBeInTheDocument()
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
      'fixed flex w-screen items-center justify-between rounded-b-xl bg-background p-4 text-foreground'
    )
  })

  test('title has correct class names and styles', () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <Header />
        </AuthProvider>
      </BrowserRouter>
    )

    const titleElement = screen.getByText('Protube')
    expect(titleElement).toHaveClass('text-start text-4xl font-bold')
  })
})
