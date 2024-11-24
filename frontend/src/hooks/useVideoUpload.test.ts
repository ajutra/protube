import { renderHook, act } from '@testing-library/react'
import { useVideoUpload } from '../hooks/useVideoUpload'
import { getEnv } from '../utils/Env'
import { useAuth } from '@/context/AuthContext'

jest.mock('../utils/Env')
jest.mock('@/context/AuthContext')

const mockGetEnv = getEnv as jest.MockedFunction<typeof getEnv>
const mockUseAuth = useAuth as jest.MockedFunction<typeof useAuth>

describe('useVideoUpload', () => {
  beforeEach(() => {
    mockGetEnv.mockReturnValue({
      API_BASE_URL: 'http://localhost:5000',
      MEDIA_BASE_URL: 'http://localhost:5000/media',
      __vite__: {},
    })

    mockUseAuth.mockReturnValue({
      isLoggedIn: true,
      username: 'testuser',
      login: jest.fn(),
      logout: jest.fn(),
    })

    // Mock URL.createObjectURL
    global.URL.createObjectURL = jest.fn(() => 'mockObjectURL')
  })

  it('should initialize with default values', () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    expect(result.current.videoFile).toBeNull()
    expect(result.current.thumbnailFile).toBeNull()
    expect(result.current.uploadProgress).toBe(0)
    expect(result.current.uploadStatus).toBe('')
    expect(result.current.title).toBe('')
    expect(result.current.description).toBe('')
  })

  it('should set video file on drop', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['video content'], 'video.mp4', { type: 'video/mp4' })

    await act(async () => {
      result.current.onDropVideo([file])
    })

    expect(result.current.videoFile).toBe(file)
    expect(result.current.uploadStatus).toBe('')
  })

  it('should set thumbnail file on drop', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['thumbnail content'], 'thumbnail.webp', {
      type: 'image/webp',
    })

    await act(async () => {
      result.current.onDropThumbnail([file])
    })

    expect(result.current.thumbnailFile).toBe(file)
    expect(result.current.uploadStatus).toBe('')
  })

  it('should set upload status to error if fields are missing', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    await act(async () => {
      result.current.handleUpload()
    })

    expect(result.current.uploadStatus).toBe(
      'Please fill in all fields and select both a video file and a thumbnail file.'
    )
  })

  it('should handle upload error', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: false,
        text: () => Promise.resolve('Upload failed'),
      })
    ) as jest.Mock

    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const videoFile = new File(['video content'], 'video.mp4', {
      type: 'video/mp4',
    })
    const thumbnailFile = new File(['thumbnail content'], 'thumbnail.webp', {
      type: 'image/webp',
    })

    await act(async () => {
      result.current.onDropVideo([videoFile])
      result.current.onDropThumbnail([thumbnailFile])
      result.current.setTitle('Test Title')
      result.current.setDescription('Test Description')
      await result.current.handleUpload()
    })

    expect(result.current.uploadStatus).toBe(
      'Please fill in all fields and select both a video file and a thumbnail file.'
    )
  })
})
