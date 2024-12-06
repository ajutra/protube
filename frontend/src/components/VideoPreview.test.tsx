import { render, fireEvent, waitFor } from '@testing-library/react'
import '@testing-library/jest-dom'
import VideoPreview from './VideoPreview'
import { VideoPreviewData } from '@/model/VideoPreviewData'

jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    MEDIA_BASE_URL: 'http://mockedurl.com',
  }),
}))

const mockVideoData: VideoPreviewData = {
  videoId: 'test-video-id',
  videoFileName: 'test-video.mp4',
  thumbnailFileName: 'test-thumbnail.jpg',
  title: 'Test Video',
  username: 'testuser',
}

test('renders VideoPreview and handles hover', async () => {
  const { getByAltText, getByRole, getByText } = render(
    <VideoPreview {...mockVideoData} />
  )

  const thumbnail = getByAltText('test-thumbnail.jpg')
  expect(thumbnail).toHaveAttribute(
    'src',
    'http://mockedurl.com/test-thumbnail.jpg'
  )

  fireEvent.mouseEnter(thumbnail)

  await waitFor(() => {
    const video = getByRole('video')
    expect(video).toBeInTheDocument()
    expect(video).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4')
    expect(video).toHaveAttribute('autoPlay')
    expect(video).toHaveAttribute('loop')
  })

  fireEvent.mouseLeave(thumbnail)

  expect(thumbnail).toHaveAttribute(
    'src',
    'http://mockedurl.com/test-thumbnail.jpg'
  )

  expect(getByText('T')).toBeInTheDocument()
  expect(getByText('Test Video')).toBeInTheDocument()
})
