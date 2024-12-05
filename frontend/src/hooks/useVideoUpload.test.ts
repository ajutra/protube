import { renderHook, act } from '@testing-library/react'
import { useVideoUpload } from '../hooks/useVideoUpload'
import { useAuth } from '@/context/AuthContext'

jest.mock('@/context/AuthContext')

const mockUseAuth = useAuth as jest.MockedFunction<typeof useAuth>

// Mock getEnv function
jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    API_BASE_URL: 'http://mock-api.com',
    MEDIA_BASE_URL: 'http://mock-media.com',
  }),
}))

describe('useVideoUpload', () => {
  beforeEach(() => {
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
    expect(result.current.videoMetadata).toBeNull()
    expect(result.current.videoError).toBe('')
    expect(result.current.thumbnailError).toBe('')
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

    expect(result.current.showFillAllFieldsError).toBe(true)
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
    })

    await act(async () => {
      result.current.setTitle('Test Title')
      result.current.setDescription('Test Description')
      result.current.setVideoMetadata({
        duration: 60,
        width: 1280,
        height: 720,
      })
    })

    await act(async () => {
      await result.current.handleUpload()
    })

    expect(result.current.uploadStatus).toBe('Upload failed: Upload failed')
  })

  it('should validate video metadata', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['video content'], 'video.mp4', { type: 'video/mp4' })
    const validMetadata = {
      duration: 60,
      width: 1280,
      height: 720,
    }

    await act(async () => {
      result.current.onDropVideo([file])
      // Simulate video metadata extraction
      const isValid = result.current.validateVideoMetadata(validMetadata)
      if (isValid) {
        result.current.setVideoMetadata(validMetadata)
      }
    })

    expect(result.current.videoMetadata).toEqual(validMetadata)
  })

  it('should set video error if metadata is invalid', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['video content'], 'video.mp4', { type: 'video/mp4' })
    const invalidMetadata = {
      duration: -1, // Invalid duration
      width: 5000, // Valid width
      height: 1000, // Valid height
    }

    await act(async () => {
      result.current.onDropVideo([file])
      const isValid = result.current.validateVideoMetadata(invalidMetadata)
      if (!isValid) {
        result.current.setVideoMetadata(null)
        result.current.setVideoFile(null)
      }
    })

    expect(result.current.videoFile).toBeNull()
    expect(result.current.videoError).toBe(
      'Video duration must be a positive number.'
    )
  })

  it('should set thumbnail error if invalid file type is dropped', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['thumbnail content'], 'thumbnail.txt', {
      type: 'text/plain',
    })

    await act(async () => {
      result.current.onDropThumbnail([file])
    })

    expect(result.current.thumbnailFile).toBeNull()
    expect(result.current.thumbnailError).toBe(
      'Please select a valid image file (JPEG, PNG, GIF, WebP, AVIF).'
    )
  })

  it('should set video error if invalid video file is dropped', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['video content'], 'video.txt', {
      type: 'text/plain',
    })

    await act(async () => {
      result.current.onDropVideo([file])
    })

    expect(result.current.videoFile).toBeNull()
    expect(result.current.videoError).toBe(
      'Please select a valid video file (MP4, WebM, Ogg).'
    )
  })

  it('should handle successful upload', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ message: 'Upload successful' }),
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
    })

    // Ensure the state is set correctly before calling handleUpload
    expect(result.current.videoFile).toBe(videoFile)
    expect(result.current.thumbnailFile).toBe(thumbnailFile)

    await act(async () => {
      result.current.setTitle('Test Title')
      result.current.setDescription('Test Description')
      result.current.setVideoMetadata({
        duration: 60,
        width: 1280,
        height: 720,
      })
    })

    // Ensure the state is set correctly before calling handleUpload
    expect(result.current.title).toBe('Test Title')
    expect(result.current.description).toBe('Test Description')
    expect(result.current.videoMetadata).toEqual({
      duration: 60,
      width: 1280,
      height: 720,
    })

    await act(async () => {
      await result.current.handleUpload()
    })

    expect(result.current.uploadStatus).toBe('Upload successful!')
  })

  it('should set video error if metadata width is invalid', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['video content'], 'video.mp4', { type: 'video/mp4' })
    const invalidMetadata = {
      duration: 60,
      width: 500, // Invalid width
      height: 1000, // Valid height
    }

    await act(async () => {
      result.current.onDropVideo([file])
      const isValid = result.current.validateVideoMetadata(invalidMetadata)
      if (!isValid) {
        result.current.setVideoMetadata(null)
        result.current.setVideoFile(null)
      }
    })

    expect(result.current.videoFile).toBeNull()
    expect(result.current.videoError).toBe(
      'Video width must be between 640 and 7680.'
    )
  })

  it('should set video error if metadata height is invalid', async () => {
    const { result } = renderHook(() => useVideoUpload(jest.fn()))

    const file = new File(['video content'], 'video.mp4', { type: 'video/mp4' })
    const invalidMetadata = {
      duration: 60,
      width: 1280, // Valid width
      height: 400, // Invalid height
    }

    await act(async () => {
      result.current.onDropVideo([file])
      const isValid = result.current.validateVideoMetadata(invalidMetadata)
      if (!isValid) {
        result.current.setVideoMetadata(null)
        result.current.setVideoFile(null)
      }
    })

    expect(result.current.videoFile).toBeNull()
    expect(result.current.videoError).toBe(
      'Video height must be between 480 and 4320.'
    )
  })
})
