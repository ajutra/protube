import { render, screen, act } from '@testing-library/react'
import '@testing-library/jest-dom'
import Cookies from 'js-cookie'
import { AuthProvider, useAuth } from './AuthContext'

// Mock Cookies
jest.mock('js-cookie', () => ({
  get: jest.fn(),
  set: jest.fn(),
  remove: jest.fn(),
}))

const TestComponent = () => {
  const { isLoggedIn, username, login, logout } = useAuth()

  return (
    <div>
      <div data-testid="isLoggedIn">{isLoggedIn.toString()}</div>
      <div data-testid="username">{username}</div>
      <button onClick={() => login('testuser')}>Login</button>
      <button onClick={logout}>Logout</button>
    </div>
  )
}

describe('AuthContext', () => {
  beforeEach(() => {
    ;(Cookies.get as jest.Mock).mockClear()
    ;(Cookies.set as jest.Mock).mockClear()
    ;(Cookies.remove as jest.Mock).mockClear()
  })

  test('initial state is correct', () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('false')
    expect(screen.getByTestId('username')).toHaveTextContent('')
  })

  test('login function works correctly', () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    act(() => {
      screen.getByText('Login').click()
    })

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('true')
    expect(screen.getByTestId('username')).toHaveTextContent('testuser')
    expect(Cookies.set).toHaveBeenCalledWith('username', 'testuser', {
      expires: 7,
      sameSite: 'Lax',
    })
  })

  test('logout function works correctly', () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    act(() => {
      screen.getByText('Login').click()
    })

    act(() => {
      screen.getByText('Logout').click()
    })

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('false')
    expect(screen.getByTestId('username')).toHaveTextContent('')
    expect(Cookies.remove).toHaveBeenCalledWith('username')
  })

  test('useAuth throws error when used outside AuthProvider', () => {
    const consoleError = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {})

    expect(() => render(<TestComponent />)).toThrow(
      'useAuth must be used within an AuthProvider'
    )

    consoleError.mockRestore()
  })

  test('initial state with stored username', () => {
    ;(Cookies.get as jest.Mock).mockReturnValue('storeduser')

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('true')
    expect(screen.getByTestId('username')).toHaveTextContent('storeduser')
  })

  test('initial state with no stored username', () => {
    ;(Cookies.get as jest.Mock).mockReturnValue(undefined)

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('false')
    expect(screen.getByTestId('username')).toHaveTextContent('')
  })
})
