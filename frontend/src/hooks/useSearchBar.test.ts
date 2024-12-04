import { renderHook, act, waitFor } from '@testing-library/react'
import useSearchBar from './useSearchBar'

// Mock getEnv function
jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    API_BASE_URL: 'http://mock-api.com',
    MEDIA_BASE_URL: 'http://mock-media.com',
  }),
}))

describe('useSearchBar', () => {
  test('clearInput should set inputValue to an empty string', () => {
    const { result } = renderHook(() => useSearchBar())

    act(() => {
      result.current.clearInput()
    })

    expect(result.current.inputValue).toBe('')
  })

  test('clearInput should set searchResults to an empty array', () => {
    const { result } = renderHook(() => useSearchBar())

    act(() => {
      result.current.clearInput()
    })

    expect(result.current.searchResults).toEqual([])
  })

  test('handleInputChange should update inputValue', () => {
    const { result } = renderHook(() => useSearchBar())

    act(() => {
      result.current.handleInputChange({
        target: { value: 'test' },
      } as React.ChangeEvent<HTMLInputElement>)
    })

    expect(result.current.inputValue).toBe('test')
  })

  test('handleSearch should update searchResults', async () => {
    const { result } = renderHook(() => useSearchBar())

    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve([{ id: '1', title: 'Test Video' }]),
      })
    ) as jest.Mock

    act(() => {
      result.current.handleInputChange({
        target: { value: 'test' },
      } as React.ChangeEvent<HTMLInputElement>)
    })

    await waitFor(() =>
      expect(result.current.searchResults).toEqual([
        { id: '1', title: 'Test Video' },
      ])
    )
  })

  test('handleSearch should set searchResults to an empty array if query is empty', async () => {
    const { result } = renderHook(() => useSearchBar())

    act(() => {
      result.current.handleInputChange({
        target: { value: ' ' },
      } as React.ChangeEvent<HTMLInputElement>)
    })

    await waitFor(() => expect(result.current.searchResults).toEqual([]))
  })

  test('handleSearch should handle fetch error', async () => {
    const { result } = renderHook(() => useSearchBar())

    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Fetch error'))
    ) as jest.Mock

    act(() => {
      result.current.handleInputChange({
        target: { value: 'test' },
      } as React.ChangeEvent<HTMLInputElement>)
    })

    await waitFor(() => expect(result.current.searchResults).toEqual([]))
  })

  test('handleMouseDown should prevent default if clicking inside searchResultsRef', () => {
    const { result } = renderHook(() => useSearchBar())
    const event = {
      preventDefault: jest.fn(),
      target: document.createElement('div'),
    } as unknown as React.MouseEvent<HTMLDivElement>

    result.current.searchResultsRef.current = document.createElement('div')
    result.current.searchResultsRef.current.appendChild(event.target as Node)

    act(() => {
      result.current.handleMouseDown(event)
    })

    expect(event.preventDefault).toHaveBeenCalled()
  })

  test('handleLinkClick should set isSearching to false', () => {
    const { result } = renderHook(() => useSearchBar())

    act(() => {
      result.current.handleLinkClick()
    })

    expect(result.current.isSearching).toBe(false)
  })
})
