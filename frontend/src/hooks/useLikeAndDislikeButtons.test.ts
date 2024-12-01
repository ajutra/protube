import { renderHook, act, waitFor } from '@testing-library/react'
import { useLikeAndDislike } from './useLikeAndDislikeButtons'

jest.mock('@/utils/Env', () => ({
  getEnv: jest.fn(() => ({
    API_BASE_URL: 'http://mockapi.com',
  })),
}))

describe('useLikeAndDislikeButtons', () => {
  const videoId = '123'
  const username = 'testuser'

  beforeEach(() => {
    jest.clearAllMocks()
  })

  test('should fetch user like or dislike status successfully', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ hasLiked: true, hasDisliked: false }),
      })
    ) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await waitFor(() => {
      expect(result.current.isLiked).toBe(true)
      expect(result.current.isDisliked).toBe(false)
    })
  })

  test('should handle error when fetching user like or dislike status', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: false })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await waitFor(() => {
      expect(result.current.isLiked).toBe(false)
      expect(result.current.isDisliked).toBe(false)
    })
  })

  test('should like a video successfully', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: true })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await act(async () => {
      await result.current.like()
    })

    expect(global.fetch).toHaveBeenCalledWith(
      'http://mockapi.com/users/testuser/videos/123/like',
      { method: 'POST' }
    )
  })

  test('should handle error when liking a video', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: false })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await expect(result.current.like()).rejects.toThrow()
  })

  test('should dislike a video successfully', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: true })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await act(async () => {
      await result.current.dislike()
    })

    expect(global.fetch).toHaveBeenCalledWith(
      'http://mockapi.com/users/testuser/videos/123/dislike',
      { method: 'POST' }
    )
  })

  test('should handle error when disliking a video', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: false })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await expect(result.current.dislike()).rejects.toThrow()
  })

  test('should remove like or dislike successfully', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: true })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await act(async () => {
      await result.current.removeLikeOrDislike()
    })

    expect(global.fetch).toHaveBeenCalledWith(
      'http://mockapi.com/users/testuser/videos/123/like',
      { method: 'DELETE' }
    )
  })

  test('should handle error when removing like or dislike', async () => {
    global.fetch = jest.fn(() => Promise.resolve({ ok: false })) as jest.Mock

    const { result } = renderHook(() =>
      useLikeAndDislike({ videoId, username })
    )

    await expect(result.current.removeLikeOrDislike()).rejects.toThrow()
  })
})
