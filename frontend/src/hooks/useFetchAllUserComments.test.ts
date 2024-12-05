import { renderHook, waitFor } from '@testing-library/react';
import useFetchAllUserComments from './useFetchAllUserComments';
import fetchMock from 'jest-fetch-mock';

jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    API_BASE_URL: 'http://mockedurl.com'
  })
}));

fetchMock.enableMocks();

describe('useFetchAllUserComments', () => {
  beforeEach(() => {
    fetchMock.resetMocks();
  });

  test('fetches and returns user comments', async () => {
    const mockComments = [{ text: 'Comment 1' }, { text: 'Comment 2' }];
    fetchMock.mockResponseOnce(JSON.stringify(mockComments));

    const { result } = renderHook(() => useFetchAllUserComments('testuser'));

    await waitFor(() => {
      expect(result.current).toEqual(mockComments);
    });

    expect(fetchMock).toHaveBeenCalledWith('http://mockedurl.com/users/testuser/comments');
  });

  test('returns an empty array when username is empty', async () => {
    const { result } = renderHook(() => useFetchAllUserComments(''));

    await waitFor(() => {
      expect(result.current).toEqual([]);
    });

    expect(fetchMock).not.toHaveBeenCalled();
  });
});