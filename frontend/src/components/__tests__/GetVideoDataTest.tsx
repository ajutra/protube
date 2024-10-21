import axios from 'axios';
import { act } from 'react';
import { renderHook } from '@testing-library/react';
import { useState } from 'react';

jest.mock('axios');

const useVideoData = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);

  const getVideoData = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/videos');
      console.log(response.data);
      setData(response.data);
    } catch (error) {
      console.error('Error fetching data', error);
    } finally {
      setLoading(false);
    }
  };

  return { getVideoData, data, loading };
};

describe('getVideoData', () => {
  test('fetches and sets video data correctly', async () => {
    
    const mockData = [{"title":"Video 1"},
        {"title":"Video 11"},
        {"title":"Video 12"},
        {"title":"Video 13"},
        {"title":"Video 14"},
        {"title":"Video 15"},
        {"title":"Video 16"},
        {"title":"Video 17"},
        {"title":"Video 18"},
        {"title":"Video 19"},
        {"title":"Video 10"},
        {"title":"Video 111"},
        {"title":"Video 112"}];
    (axios.get as jest.Mock).mockResolvedValue({ data: mockData });

    const { result } = renderHook(() => useVideoData());

    
    await act(async () => {
      await result.current.getVideoData();
    });

    
    expect(result.current.data).toEqual(mockData);
    expect(result.current.loading).toBe(false);
  });

  test('sets loading state correctly during API call', async () => {
    (axios.get as jest.Mock).mockResolvedValue({ data: [] });

    const { result } = renderHook(() => useVideoData());

    
    act(() => {
      result.current.getVideoData();
    });
    expect(result.current.loading).toBe(true);

    
    await act(async () => {
      await result.current.getVideoData();
    });
    expect(result.current.loading).toBe(false);
  });

  test('handles errors correctly', async () => {
    const errorMessage = 'Network error';
    (axios.get as jest.Mock).mockRejectedValue(new Error(errorMessage));

    const { result } = renderHook(() => useVideoData());

   
    const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation();

    await act(async () => {
      await result.current.getVideoData();
    });

    
    expect(consoleErrorSpy).toHaveBeenCalledWith('Error fetching data', expect.any(Error));
    expect(result.current.loading).toBe(false);

    consoleErrorSpy.mockRestore(); 
  });
});
