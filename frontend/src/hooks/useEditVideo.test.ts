import { renderHook, act } from '@testing-library/react'
import { useEditVideo } from '../hooks/useEditVideo'
import { getEnv } from '@/utils/Env'
import { useToast } from '@/hooks/use-toast'

jest.mock('@/utils/Env')
jest.mock('@/hooks/use-toast')

const mockGetEnv = getEnv as jest.MockedFunction<typeof getEnv>
const mockUseToast = useToast as jest.MockedFunction<typeof useToast>

describe('useEditVideo', () => {
  beforeEach(() => {
    mockGetEnv.mockReturnValue({
      API_BASE_URL: 'http://localhost:5000',
      MEDIA_BASE_URL: 'http://localhost:5000/media',
      __vite__: {
        BASE_URL: '/',
        MODE: 'development',
        DEV: true,
        PROD: false,
        SSR: false,
      },
      API_VIDEOS_URL: '',
    })

    mockUseToast.mockReturnValue({
      toast: jest.fn(),
      dismiss: jest.fn(),
      toasts: [],
    })
  })

  const video = {
    videoId: '123',
    title: 'Test Video',
    description: 'Test Description',
    width: 1920,
    height: 1080,
    duration: 120,
    username: 'testuser',
    meta: {
      comments: [],
    },
  }

  const onSave = jest.fn()

  it('should initialize with default values', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    expect(result.current.title).toBe(video.title)
    expect(result.current.description).toBe(video.description)
  })

  it('should update title', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    act(() => {
      result.current.setTitle('New Title')
    })

    expect(result.current.title).toBe('New Title')
  })

  it('should update description', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    act(() => {
      result.current.setDescription('New Description')
    })

    expect(result.current.description).toBe('New Description')
  })

  it('should handle save successfully', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({}),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useEditVideo(video, onSave))

    await act(async () => {
      await result.current.handleSave()
    })

    expect(global.fetch).toHaveBeenCalledWith(
      'http://localhost:5000/videos',
      expect.objectContaining({
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: video.videoId,
          title: 'Test Video',
          description: 'Test Description',
        }),
      })
    )
    expect(onSave).toHaveBeenCalled()
    expect(mockUseToast().toast).toHaveBeenCalledWith({
      description: 'Video updated successfully!',
    })
  })

  it('should handle save error', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: false,
        text: () => Promise.resolve('Failed to update video'),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useEditVideo(video, onSave))

    await act(async () => {
      await result.current.handleSave()
    })

    expect(mockUseToast().toast).toHaveBeenCalledWith({
      description: 'Failed to update video: Failed to update video',
    })
  })

  it('should handle save exception', async () => {
    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Network error'))
    ) as jest.Mock

    const { result } = renderHook(() => useEditVideo(video, onSave))

    await act(async () => {
      await result.current.handleSave()
    })

    expect(mockUseToast().toast).toHaveBeenCalledWith({
      description: 'An error occurred: Network error',
    })
  })
})
