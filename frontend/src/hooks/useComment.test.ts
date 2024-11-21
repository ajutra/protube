import { renderHook, act, waitFor } from '@testing-library/react'
import { useComment } from './useComment'
import { Comment as CommentType } from '@/model/Comment'
import { getEnv } from '@/utils/Env'

jest.mock('@/utils/Env', () => ({
  getEnv: jest.fn(),
}))

const mockComment: CommentType = {
  commentId: '1',
  text: 'Initial comment',
  videoId: '123',
  username: 'testuser',
}

describe('useComment', () => {
  beforeEach(() => {
    jest.clearAllMocks()
  })

  test('should initialize with correct values', () => {
    const { result } = renderHook(() => useComment(mockComment))

    expect(result.current.isEditing).toBe(false)
    expect(result.current.commentText).toBe(mockComment.text)
    expect(result.current.isLoading).toBe(false)
    expect(result.current.showError).toBe(false)
  })

  test('should handle edit state', () => {
    const { result } = renderHook(() => useComment(mockComment))

    act(() => {
      result.current.setIsEditing(true)
    })

    expect(result.current.isEditing).toBe(true)

    act(() => {
      result.current.setIsEditing(false)
    })

    expect(result.current.isEditing).toBe(false)
  })

  test('should handle cancel edit', () => {
    const { result } = renderHook(() => useComment(mockComment))

    act(() => {
      result.current.setIsEditing(true)
    })

    expect(result.current.isEditing).toBe(true)

    act(() => {
      result.current.handleOnCancel()
    })

    expect(result.current.isEditing).toBe(false)
  })

  test('should handle confirm edit successfully', async () => {
    ;(getEnv as jest.Mock).mockReturnValue({
      API_BASE_URL: 'http://mockapi.com',
    })

    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({}),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useComment(mockComment))

    act(() => {
      result.current.setIsEditing(true)
    })

    act(() => {
      result.current.handleOnConfirm('Updated comment')
    })

    expect(result.current.isEditing).toBe(false)
    expect(result.current.isLoading).toBe(true)

    await waitFor(() => !result.current.isLoading)

    expect(result.current.commentText).toBe('Updated comment')
    expect(result.current.isLoading).toBe(false)
    expect(result.current.showError).toBe(false)
  })

  test('should handle confirm edit failure', async () => {
    ;(getEnv as jest.Mock).mockReturnValue({
      API_BASE_URL: 'http://mockapi.com',
    })

    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: false,
        json: () => Promise.resolve({}),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useComment(mockComment))

    act(() => {
      result.current.setIsEditing(true)
    })

    act(() => {
      result.current.handleOnConfirm('Updated comment')
    })

    expect(result.current.isEditing).toBe(false)
    expect(result.current.isLoading).toBe(true)

    await waitFor(() => !result.current.isLoading)

    expect(result.current.commentText).toBe(mockComment.text)
    expect(result.current.isLoading).toBe(false)
    expect(result.current.showError).toBe(true)
  })

  test('should handle network error', async () => {
    ;(getEnv as jest.Mock).mockReturnValue({
      API_BASE_URL: 'http://mockapi.com',
    })

    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Network error'))
    ) as jest.Mock

    const { result } = renderHook(() => useComment(mockComment))

    act(() => {
      result.current.setIsEditing(true)
    })

    act(() => {
      result.current.handleOnConfirm('Updated comment')
    })

    expect(result.current.isEditing).toBe(false)
    expect(result.current.isLoading).toBe(true)

    await waitFor(() => !result.current.isLoading)

    expect(result.current.commentText).toBe(mockComment.text)
    expect(result.current.isLoading).toBe(false)
    expect(result.current.showError).toBe(true)
  })
})
