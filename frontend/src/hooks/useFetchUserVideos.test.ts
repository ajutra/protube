import { renderHook, waitFor } from '@testing-library/react';
import useFetchUserVideos from './useFetchUserVideos';
import fetchMock from 'jest-fetch-mock';
import { VideoPreviewData } from '@/model/VideoPreviewData';

jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    API_BASE_URL: 'http://mockedurl.com'
  })
}));

fetchMock.enableMocks();

describe('useFetchUserVideos', () => {
  beforeEach(() => {
    fetchMock.resetMocks();
  });

  test('fetches and returns user videos', async () => {
    const mockVideos: VideoPreviewData[] = [
      { videoId: '1', videoFileName: 'video1.mp4', thumbnailFileName: 'thumb1.jpg', title: 'Video 1', username: 'user1' },
      { videoId: '2', videoFileName: 'video2.mp4', thumbnailFileName: 'thumb2.jpg', title: 'Video 2', username: 'user2' }
    ];

    fetchMock.mockResponseOnce(JSON.stringify(mockVideos));

    const { result } = renderHook(() => useFetchUserVideos('testuser'));

    await waitFor(() => {
      expect(result.current.videos).toEqual(mockVideos);
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toBeNull();
    });

    expect(fetchMock).toHaveBeenCalledWith('http://mockedurl.com/users/testuser/videos');
  });

  test('handles error when fetching user videos', async () => {
    fetchMock.mockRejectOnce(new Error('Failed to fetch videos'));

    const { result } = renderHook(() => useFetchUserVideos('testuser'));

    await waitFor(() => {
      expect(result.current.videos).toEqual([]);
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toEqual(new Error('Failed to fetch videos'));
    });

    expect(fetchMock).toHaveBeenCalledWith('http://mockedurl.com/users/testuser/videos');
  });

  test('returns empty array when no videos are found', async () => {
    fetchMock.mockResponseOnce(JSON.stringify([]));

    const { result } = renderHook(() => useFetchUserVideos('testuser'));

    await waitFor(() => {
      expect(result.current.videos).toEqual([]);
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toBeNull();
    });

    expect(fetchMock).toHaveBeenCalledWith('http://mockedurl.com/users/testuser/videos');
  });

  test('shows error message when fetching user videos fails', async () => {
    fetchMock.mockResponseOnce(JSON.stringify({ message: 'Failed to fetch videos' }), { status: 500 });

    const { result } = renderHook(() => useFetchUserVideos('testuser'));

    await waitFor(() => {
      expect(result.current.videos).toEqual([]);
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toEqual(new Error('Failed to fetch videos'));
    });

    expect(fetchMock).toHaveBeenCalledWith('http://mockedurl.com/users/testuser/videos');
  });
});