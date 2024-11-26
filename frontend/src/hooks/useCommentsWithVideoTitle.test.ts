import { renderHook, act } from '@testing-library/react'
import useCommentsWithVideoTitle from './useCommentsWithVideoTitle'
import { getEnv } from '@/utils/Env'

jest.mock('@/hooks/useFetchAllUserComments')
jest.mock('@/hooks/useGetVideoDetailsForComments', () => ({
  __esModule: true,
  default: jest.fn(),
}))

jest.mock('@/utils/Env', () => ({
  getEnv: jest.fn(),
}))

const mockVideoDetails = {
  video1: { title: 'Video 1', username: 'user1' },
  video2: { title: 'Video 2', username: 'user2' },
}

const mockGroupedComments = {
  video1: [
    { commentId: '1', videoId: 'video1', text: 'Comment 1' },
    { commentId: '2', videoId: 'video1', text: 'Comment 2' },
  ],
  video2: [{ commentId: '3', videoId: 'video2', text: 'Comment 3' }],
}

describe('useCommentsWithVideoTitle', () => {
  beforeEach(() => {
    jest.clearAllMocks()
    ;(getEnv as jest.Mock).mockReturnValue({
      API_BASE_URL: 'http://mockapi.com',
    })
    require('@/hooks/useGetVideoDetailsForComments').default.mockReturnValue({
      videoDetails: mockVideoDetails,
      groupedComments: mockGroupedComments,
    })
  })

  test('should initialize with video details and grouped comments', () => {
    const { result } = renderHook(() => useCommentsWithVideoTitle('user1'))

    expect(result.current.videoDetails).toEqual(mockVideoDetails)
    expect(result.current.groupedComments).toEqual(mockGroupedComments)
    expect(result.current.hasComments).toBe(true)
  })

  test('should update grouped comments when initialGroupedComments changes', () => {
    const { result, rerender } = renderHook(() =>
      useCommentsWithVideoTitle('user1')
    )

    expect(result.current.groupedComments).toEqual(mockGroupedComments)

    const newGroupedComments = {
      video1: [{ commentId: '1', videoId: 'video1', text: 'Comment 1' }],
    }

    require('@/hooks/useGetVideoDetailsForComments').default.mockReturnValue({
      videoDetails: mockVideoDetails,
      groupedComments: newGroupedComments,
    })

    rerender()

    expect(result.current.groupedComments).toEqual(newGroupedComments)
  })

  test('should handle deleted comment and update grouped comments', () => {
    const { result } = renderHook(() => useCommentsWithVideoTitle('user1'))

    act(() => {
      result.current.handleDeletedComment('video1', '1')
    })

    expect(result.current.groupedComments.video1).toEqual([
      { commentId: '2', videoId: 'video1', text: 'Comment 2' },
    ])
  })

  test('should remove video entry when last comment is deleted', () => {
    const { result } = renderHook(() => useCommentsWithVideoTitle('user1'))

    act(() => {
      result.current.handleDeletedComment('video2', '3')
    })

    expect(result.current.groupedComments.video2).toBeUndefined()
  })

  test('should return hasComments as false when there are no comments', () => {
    require('@/hooks/useGetVideoDetailsForComments').default.mockReturnValue({
      videoDetails: {},
      groupedComments: {},
    })

    const { result } = renderHook(() => useCommentsWithVideoTitle('user1'))

    expect(result.current.hasComments).toBe(false)
  })

  test('should handle empty initialGroupedComments', () => {
    require('@/hooks/useGetVideoDetailsForComments').default.mockReturnValue({
      videoDetails: {},
      groupedComments: {},
    })

    const { result } = renderHook(() => useCommentsWithVideoTitle('user1'))

    expect(result.current.groupedComments).toEqual({})
    expect(result.current.hasComments).toBe(false)
  })

  test('should handle non-existent videoId in handleDeletedComment', () => {
    const { result } = renderHook(() => useCommentsWithVideoTitle('user1'))

    act(() => {
      result.current.handleDeletedComment('nonExistentVideoId', '1')
    })

    expect(result.current.groupedComments).toEqual(mockGroupedComments)
  })
})
