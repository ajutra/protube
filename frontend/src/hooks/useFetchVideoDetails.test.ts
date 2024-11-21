import { renderHook, waitFor } from '@testing-library/react'
import useFetchVideoDetails from './useFetchVideoDetails'
import { VideoPreviewData } from '@/model/VideoPreviewData'

global.clearImmediate = global.clearImmediate || ((id: any) => clearTimeout(id))
global.setImmediate = (global.setImmediate ||
  ((fn: (...args: any[]) => void) => setTimeout(fn, 0))) as typeof setImmediate

jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    API_BASE_URL: 'http://mockedurl.com',
  }),
}))

const mockVideo: VideoPreviewData = {
  videoId: '1',
  videoFileName: 'test-video.mp4',
  thumbnailFileName: 'test-thumbnail.jpg',
  title: 'Test Video',
  username: 'TestUser',
  meta: {
    tags: [{ tagName: 'Tag1' }, { tagName: 'Tag2' }],
    categories: [{ categoryName: 'Category1' }, { categoryName: 'Category2' }],
    comments: [
      { videoId: '1', username: 'User1', text: 'Comment1' },
      { videoId: '2', username: 'User2', text: 'Comment2' },
    ],
  },
}

describe('useFetchVideoDetails', () => {
  beforeEach(() => {
    jest.clearAllMocks()
    jest.useFakeTimers()
  })

  afterEach(() => {
    jest.runOnlyPendingTimers()
    jest.useRealTimers()
  })

  test('should initialize with null video, loading true, and no error', () => {
    const { result } = renderHook(() => useFetchVideoDetails('1'))

    expect(result.current.video).toBeNull()
    expect(result.current.loading).toBe(true)
    expect(result.current.error).toBeNull()
  })

  test('should fetch video details successfully', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(mockVideo),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useFetchVideoDetails('1'))

    await waitFor(() => expect(result.current.loading).toBe(false))

    expect(result.current.video).toEqual(mockVideo)
    expect(result.current.loading).toBe(false)
    expect(result.current.error).toBeNull()
  })

  test('should handle invalid video ID', async () => {
    const { result } = renderHook(() => useFetchVideoDetails(null))

    expect(result.current.loading).toBe(false)
    expect(result.current.video).toBeNull()
    expect(result.current.error).toEqual(new Error('Invalid video ID'))
  })

  test('should handle network error', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: false,
      })
    ) as jest.Mock

    const { result } = renderHook(() => useFetchVideoDetails('1'))

    await waitFor(() => expect(result.current.loading).toBe(false))

    expect(result.current.video).toBeNull()
    expect(result.current.loading).toBe(false)
    expect(result.current.error).toEqual(
      new Error('Network response was not ok')
    )
  })

  test('should handle fetch error', async () => {
    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Fetch error'))
    ) as jest.Mock

    const { result } = renderHook(() => useFetchVideoDetails('1'))

    await waitFor(() => expect(result.current.loading).toBe(false))

    expect(result.current.video).toBeNull()
    expect(result.current.loading).toBe(false)
    expect(result.current.error).toEqual(new Error('Fetch error'))
  })
})
