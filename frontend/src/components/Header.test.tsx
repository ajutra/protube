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
})
