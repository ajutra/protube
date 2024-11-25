import { renderHook, act } from '@testing-library/react'
import useDeleteVideo from './useDeleteVideo'
import { AppRoutes } from '@/enums/AppRoutes'

jest.mock('react-router-dom', () => ({
  useNavigate: jest.fn(),
}))

jest.mock('@/utils/Env', () => ({
  getEnv: jest.fn(),
}))

jest.mock('@/hooks/use-toast', () => ({
  useToast: jest.fn(),
}))

describe('useDeleteVideo', () => {
  const mockNavigate = jest.fn()
  const mockToast = jest.fn()

  beforeEach(() => {
    jest.clearAllMocks()
    require('react-router-dom').useNavigate.mockReturnValue(mockNavigate)
    require('@/hooks/use-toast').useToast.mockReturnValue({ toast: mockToast })
    require('@/utils/Env').getEnv.mockReturnValue({
      API_VIDEOS_URL: 'http://mockurl.com',
    })
  })

  test('should initialize with correct state', () => {
    const { result } = renderHook(() => useDeleteVideo('123', true))

    expect(result.current.isLoading).toBe(false)
    expect(result.current.showErrorDeletingVideo).toBe(false)
  })

  test('should handle successful deletion', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
      })
    ) as jest.Mock

    const { result } = renderHook(() => useDeleteVideo('123', true))

    await act(async () => {
      await result.current.handleOnDeleteVideo()
    })

    expect(result.current.isLoading).toBe(false)
    expect(result.current.showErrorDeletingVideo).toBe(false)
    expect(mockToast).toHaveBeenCalledWith({
      description: 'Video deleted successfully',
    })
    expect(mockNavigate).toHaveBeenCalledWith(AppRoutes.HOME)
  })

  test('should handle failed deletion', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: false,
      })
    ) as jest.Mock

    const { result } = renderHook(() => useDeleteVideo('123', true))

    await act(async () => {
      await result.current.handleOnDeleteVideo()
    })

    expect(result.current.isLoading).toBe(false)
    expect(result.current.showErrorDeletingVideo).toBe(true)
    expect(mockToast).not.toHaveBeenCalled()
    expect(mockNavigate).not.toHaveBeenCalled()
  })

  test('should handle network error', async () => {
    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Network error'))
    ) as jest.Mock

    const { result } = renderHook(() => useDeleteVideo('123', true))

    await act(async () => {
      await result.current.handleOnDeleteVideo()
    })

    expect(result.current.isLoading).toBe(false)
    expect(result.current.showErrorDeletingVideo).toBe(true)
    expect(mockToast).not.toHaveBeenCalled()
    expect(mockNavigate).not.toHaveBeenCalled()
  })

  test('should not proceed if videoId is undefined', async () => {
    const { result } = renderHook(() => useDeleteVideo(undefined, true))

    await act(async () => {
      await result.current.handleOnDeleteVideo()
    })

    expect(result.current.isLoading).toBe(false)
    expect(result.current.showErrorDeletingVideo).toBe(false)
    expect(global.fetch).not.toHaveBeenCalled()
    expect(mockToast).not.toHaveBeenCalled()
    expect(mockNavigate).not.toHaveBeenCalled()
  })

  test('should call onDeleteSuccess callback if provided', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
      })
    ) as jest.Mock

    const mockOnDeleteSuccess = jest.fn()
    const { result } = renderHook(() => useDeleteVideo('123', false))

    await act(async () => {
      await result.current.handleOnDeleteVideo(mockOnDeleteSuccess)
    })

    expect(result.current.isLoading).toBe(false)
    expect(result.current.showErrorDeletingVideo).toBe(false)
    expect(mockToast).toHaveBeenCalledWith({
      description: 'Video deleted successfully',
    })
    expect(mockOnDeleteSuccess).toHaveBeenCalled()
    expect(mockNavigate).not.toHaveBeenCalled()
  })
})
