import { fireEvent, render, screen } from '@testing-library/react'
import '@testing-library/jest-dom'
import { BrowserRouter } from 'react-router-dom'
import Header from './Header'
import { AppRoutes } from '../enums/AppRoutes'

const mockNavigate = jest.fn()

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}))

describe('Header Component', () => {
  test('renders the title and handles click', () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    )

    const titleElement = screen.getByText('Protube')
    expect(titleElement).toBeInTheDocument()

    fireEvent.click(titleElement)
    expect(mockNavigate).toHaveBeenCalledWith(AppRoutes.HOME)
  })

  test('header has correct class names and styles', () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    )

    const headerElement = screen.getByRole('banner')
    expect(headerElement).toHaveClass('bg-dark text-light w-100 fixed-top p-3')
  })

  test('title has correct class names and styles', () => {
    render(
      <BrowserRouter>
        <Header />
      </BrowserRouter>
    )

    const titleElement = screen.getByText('Protube')
    expect(titleElement).toHaveClass('text-start')
    expect(titleElement).toHaveStyle('cursor: pointer')
  })
})
