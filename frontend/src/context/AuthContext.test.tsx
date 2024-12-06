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

// Mock fetch
global.fetch = jest.fn()

// Mock getEnv
jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    API_LOGIN_URL: 'http://mock-login-url',
    API_REGISTER_URL: 'http://mock-register-url',
  }),
}))

const TestComponent = () => {
  const { isLoggedIn, username, login, register, logout } = useAuth()

  return (
    <div>
      <div data-testid="isLoggedIn">{isLoggedIn.toString()}</div>
      <div data-testid="username">{username}</div>
      <button onClick={() => login('testuser', 'password')}>Login</button>
      <button onClick={() => register('newuser', 'password')}>Register</button>
      <button onClick={logout}>Logout</button>
    </div>
  )
}

describe('AuthContext', () => {
  beforeEach(() => {
    ;(Cookies.get as jest.Mock).mockClear()
    ;(Cookies.set as jest.Mock).mockClear()
    ;(Cookies.remove as jest.Mock).mockClear()
    ;(global.fetch as jest.Mock).mockClear()
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

  test('login function works correctly', async () => {
    ;(global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      status: 200,
      json: async () => ({}),
    })

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    await act(async () => {
      screen.getByText('Login').click()
    })

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('true')
    expect(screen.getByTestId('username')).toHaveTextContent('testuser')
    expect(Cookies.set).toHaveBeenCalledWith('username', 'testuser', {
      expires: 7,
      sameSite: 'Lax',
    })
  })

  test('register function works correctly', async () => {
    ;(global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      status: 200,
      json: async () => ({}),
    })

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    await act(async () => {
      screen.getByText('Register').click()
    })

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('true')
    expect(screen.getByTestId('username')).toHaveTextContent('newuser')
    expect(Cookies.set).toHaveBeenCalledWith('username', 'newuser', {
      expires: 7,
      sameSite: 'Lax',
    })
  })

  test('logout function works correctly', async () => {
    ;(global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      status: 200,
      json: async () => ({}),
    })

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    await act(async () => {
      screen.getByText('Login').click()
    })

    await act(async () => {
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

  test('login function handles errors correctly', async () => {
    ;(global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
      status: 400,
      text: async () => 'Invalid username or password',
    })

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    await act(async () => {
      screen.getByText('Login').click()
    })

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('false')
    expect(screen.getByTestId('username')).toHaveTextContent('')
  })

  test('register function handles errors correctly', async () => {
    ;(global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
      status: 400,
      text: async () => 'Registration failed',
    })

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    )

    await act(async () => {
      screen.getByText('Register').click()
    })

    expect(screen.getByTestId('isLoggedIn')).toHaveTextContent('false')
    expect(screen.getByTestId('username')).toHaveTextContent('')
  })
})
