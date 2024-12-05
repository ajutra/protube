import { renderHook, waitFor } from '@testing-library/react'
import useGetVideoDetailsForComments from '@/hooks/useGetVideoDetailsForComments'
import { Comment as CommentType } from '@/model/Comment'

jest.mock('@/utils/Env', () => ({
  getEnv: jest.fn(() => ({ API_BASE_URL: 'http://mock-api.com' })),
}))

global.fetch = jest.fn()

describe('useGetVideoDetailsForComments', () => {
  const comments: CommentType[] = [
    {
      videoId: 'video1',
      commentId: 'comment1',
      username: 'User 1',
      text: 'First comment',
    },
    {
      videoId: 'video1',
      commentId: 'comment2',
      username: 'User 1',
      text: 'Second comment',
    },
    {
      videoId: 'video2',
      commentId: 'comment3',
      username: 'User 2',
      text: 'Third comment',
    },
  ]

  afterEach(() => {
    jest.clearAllMocks()
  })

  it('fetches video details correctly', async () => {
    ;(global.fetch as jest.Mock).mockImplementation((url) => {
      const videoId = url.split('/').pop()
      return Promise.resolve({
        json: () => Promise.resolve({ id: videoId, title: `Video ${videoId}` }),
      })
    })

    const { result } = renderHook(() => useGetVideoDetailsForComments(comments))

    await waitFor(() => {
      expect(result.current.videoDetails).toEqual({
        video1: { id: 'video1', title: 'Video video1' },
        video2: { id: 'video2', title: 'Video video2' },
      })
    })

    expect(fetch).toHaveBeenCalledTimes(2)
    expect(fetch).toHaveBeenCalledWith('http://mock-api.com/videos/video1')
    expect(fetch).toHaveBeenCalledWith('http://mock-api.com/videos/video2')

    expect(result.current.videoDetails).toEqual({
      video1: { id: 'video1', title: 'Video video1' },
      video2: { id: 'video2', title: 'Video video2' },
    })
  })

  it('groups comments by videoId correctly', () => {
    const { result } = renderHook(() => useGetVideoDetailsForComments(comments))

    expect(result.current.groupedComments).toEqual({
      video1: [
        {
          videoId: 'video1',
          commentId: 'comment1',
          username: 'User 1',
          text: 'First comment',
        },
        {
          videoId: 'video1',
          commentId: 'comment2',
          username: 'User 1',
          text: 'Second comment',
        },
      ],
      video2: [
        {
          videoId: 'video2',
          commentId: 'comment3',
          username: 'User 2',
          text: 'Third comment',
        },
      ],
    })
  })
})
