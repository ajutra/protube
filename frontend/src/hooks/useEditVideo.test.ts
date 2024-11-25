import { renderHook, act } from '@testing-library/react'
import { useEditVideo } from '../hooks/useEditVideo'
import { getEnv } from '@/utils/Env'
import { useToast } from '@/hooks/use-toast'

jest.mock('@/utils/Env')
jest.mock('@/hooks/use-toast')

const mockGetEnv = getEnv as jest.MockedFunction<typeof getEnv>
const mockUseToast = useToast as jest.MockedFunction<typeof useToast>

describe('useEditVideo', () => {
  beforeEach(() => {
    mockGetEnv.mockReturnValue({
      API_BASE_URL: 'http://localhost:5000',
      MEDIA_BASE_URL: 'http://localhost:5000/media',
      __vite__: {
        BASE_URL: '/',
        MODE: 'development',
        DEV: true,
        PROD: false,
        SSR: false,
      },
      API_VIDEOS_URL: '',
    })

    mockUseToast.mockReturnValue({
      toast: jest.fn(),
      dismiss: jest.fn(),
      toasts: [],
    })
  })

  const video = {
    videoId: '123',
    title: 'Test Video',
    description: 'Test Description',
    width: 1920,
    height: 1080,
    duration: 120,
    username: 'testuser',
    meta: {
      tags: [{ name: 'tag1' }, { name: 'tag2' }],
      categories: [{ name: 'category1' }, { name: 'category2' }],
      comments: [],
    },
  }

  const onSave = jest.fn()

  it('should initialize with default values', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    expect(result.current.title).toBe(video.title)
    expect(result.current.description).toBe(video.description)
    expect(result.current.tags).toBe('tag1, tag2')
    expect(result.current.categories).toBe('category1, category2')
  })

  it('should update title', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    act(() => {
      result.current.setTitle('New Title')
    })

    expect(result.current.title).toBe('New Title')
  })

  it('should update description', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    act(() => {
      result.current.setDescription('New Description')
    })

    expect(result.current.description).toBe('New Description')
  })

  it('should update tags', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    act(() => {
      result.current.setTags('newtag1, newtag2')
    })

    expect(result.current.tags).toBe('newtag1, newtag2')
  })

  it('should update categories', () => {
    const { result } = renderHook(() => useEditVideo(video, onSave))

    act(() => {
      result.current.setCategories('newcategory1, newcategory2')
    })

    expect(result.current.categories).toBe('newcategory1, newcategory2')
  })

  it('should handle save successfully', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({}),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useEditVideo(video, onSave))

    await act(async () => {
      await result.current.handleSave()
    })

    expect(global.fetch).toHaveBeenCalledWith(
      'http://localhost:5000/videos/123',
      expect.objectContaining({
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          width: video.width,
          height: video.height,
          duration: video.duration,
          title: 'Test Video',
          description: 'Test Description',
          username: 'testuser',
          tags: [{ tagName: 'tag1' }, { tagName: 'tag2' }],
          categories: [
            { categoryName: 'category1' },
            { categoryName: 'category2' },
          ],
          comments: [],
        }),
      })
    )
    expect(onSave).toHaveBeenCalled()
    expect(mockUseToast().toast).toHaveBeenCalledWith({
      description: 'Video updated successfully!',
    })
  })

  it('should handle save error', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: false,
        text: () => Promise.resolve('Failed to update video'),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useEditVideo(video, onSave))

    await act(async () => {
      await result.current.handleSave()
    })

    expect(mockUseToast().toast).toHaveBeenCalledWith({
      description: 'Failed to update video: Failed to update video',
    })
  })

  it('should handle save exception', async () => {
    global.fetch = jest.fn(() =>
      Promise.reject(new Error('Network error'))
    ) as jest.Mock

    const { result } = renderHook(() => useEditVideo(video, onSave))

    await act(async () => {
      await result.current.handleSave()
    })

    expect(mockUseToast().toast).toHaveBeenCalledWith({
      description: 'An error occurred: Network error',
    })
  })
})
