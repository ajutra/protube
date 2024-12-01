import { renderHook, act } from '@testing-library/react';
import useLeaveComment from '@/hooks/useLeaveComment';

// Mock de `getEnv`
jest.mock('@/utils/Env', () => ({
  getEnv: jest.fn(() => ({ API_BASE_URL: 'http://mock-api.com' })),
}));

// Mock de `fetch`
global.fetch = jest.fn();

describe('useLeaveComment', () => {
  const mockOnNewComment = jest.fn();
  const mockVideoId = 'video123';
  const mockUsername = 'userTest';

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should initialize with loading as false', () => {
    const { result } = renderHook(() =>
      useLeaveComment({
        videoId: mockVideoId,
        username: mockUsername,
        onNewComment: mockOnNewComment,
      })
    );

    expect(result.current.loading).toBe(false);
  });

  it('should handle successful comment submission', async () => {
    const mockCommentText = 'This is a test comment';
    (global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: true,
      json: async () => ({}),
    });

    const { result } = renderHook(() =>
      useLeaveComment({
        videoId: mockVideoId,
        username: mockUsername,
        onNewComment: mockOnNewComment,
      })
    );

    await act(async () => {
      await result.current.handleConfirm(mockCommentText);
    });

    expect(result.current.loading).toBe(false);
    expect(fetch).toHaveBeenCalledWith('http://mock-api.com/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        videoId: mockVideoId,
        username: mockUsername,
        text: mockCommentText,
      }),
    });
    expect(mockOnNewComment).toHaveBeenCalledWith({
      videoId: mockVideoId,
      username: mockUsername,
      text: mockCommentText,
    });
  });

  it('should handle failed comment submission', async () => {
    const mockCommentText = 'This is a test comment';
    (global.fetch as jest.Mock).mockResolvedValueOnce({
      ok: false,
    });

    const { result } = renderHook(() =>
      useLeaveComment({
        videoId: mockVideoId,
        username: mockUsername,
        onNewComment: mockOnNewComment,
      })
    );

    await act(async () => {
      await result.current.handleConfirm(mockCommentText);
    });

    expect(result.current.loading).toBe(false);
    expect(fetch).toHaveBeenCalledWith('http://mock-api.com/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        videoId: mockVideoId,
        username: mockUsername,
        text: mockCommentText,
      }),
    });
    expect(mockOnNewComment).not.toHaveBeenCalled();
  });

  it('should handle network error during comment submission', async () => {
    const mockCommentText = 'This is a test comment';
    (global.fetch as jest.Mock).mockRejectedValueOnce(new Error('Network error'));

    const { result } = renderHook(() =>
      useLeaveComment({
        videoId: mockVideoId,
        username: mockUsername,
        onNewComment: mockOnNewComment,
      })
    );

    await act(async () => {
      await result.current.handleConfirm(mockCommentText);
    });

    expect(result.current.loading).toBe(false);
    expect(fetch).toHaveBeenCalledWith('http://mock-api.com/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        videoId: mockVideoId,
        username: mockUsername,
        text: mockCommentText,
      }),
    });
    expect(mockOnNewComment).not.toHaveBeenCalled();
  });
});
