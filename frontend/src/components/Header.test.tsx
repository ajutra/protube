import { render, screen } from '@testing-library/react'
import '@testing-library/jest-dom'
import { BrowserRouter } from 'react-router-dom'
import Header from './Header'

describe('Header Component', () => {
  test('renders the title', () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    )

    const titleElement = screen.getByText('Protube')
    expect(titleElement).toBeInTheDocument()
  })

  test('header has correct class names and styles', () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    )

    const headerElement = screen.getByRole('banner')
    expect(headerElement).toHaveClass(
      'fixed w-screen bg-background p-3 text-foreground'
    )
  })

  test('title has correct class names and styles', () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    )

    const titleElement = screen.getByText('Protube')
    expect(titleElement).toHaveClass('text-start text-4xl font-bold')
  })
})
