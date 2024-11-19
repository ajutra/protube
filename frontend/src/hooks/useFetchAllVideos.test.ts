import { renderHook, waitFor } from '@testing-library/react'
import useFetchAllVideos from './useFetchAllVideos'
import { VideoPreviewData } from '@/model/VideoPreviewData'

global.clearImmediate = global.clearImmediate || ((id: any) => clearTimeout(id))
global.setImmediate = (global.setImmediate ||
  ((fn: (...args: any[]) => void) => setTimeout(fn, 0))) as typeof setImmediate

const mockVideos: VideoPreviewData[] = [
  {
    videoFileName: 'video1.mp4',
    thumbnailFileName: 'thumbnail1.jpg',
    title: 'Video 1',
    username: 'user1',
    videoId: '',
  },
  {
    videoFileName: 'video2.mp4',
    thumbnailFileName: 'thumbnail2.jpg',
    title: 'Video 2',
    username: 'user2',
    videoId: '',
  },
]

describe('useFetchAllVideos', () => {
  beforeEach(() => {
    jest.clearAllMocks()
    jest.useFakeTimers()
  })

  afterEach(() => {
    jest.runOnlyPendingTimers()
    jest.useRealTimers()
  })

  test('should initialize with empty videos, loading true, and no error', () => {
    const { result } = renderHook(() => useFetchAllVideos('http://mockurl.com'))

    expect(result.current.videos).toEqual([])
    expect(result.current.loading).toBe(true)
    expect(result.current.error).toBeNull()
  })

  test('should fetch videos successfully', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockVideos),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useFetchAllVideos('http://mockurl.com'))

    await waitFor(() => expect(result.current.loading).toBe(false))

    expect(result.current.videos).toEqual(mockVideos)
    expect(result.current.loading).toBe(false)
    expect(result.current.error).toBeNull()
  })

  test('should handle network error', async () => {
    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Network error'))
    ) as jest.Mock

    const { result } = renderHook(() => useFetchAllVideos('http://mockurl.com'))

    await waitFor(() => expect(result.current.loading).toBe(false))

    expect(result.current.videos).toEqual([])
    expect(result.current.loading).toBe(false)
    expect(result.current.error).toEqual(new Error('Network error'))
  })
})
