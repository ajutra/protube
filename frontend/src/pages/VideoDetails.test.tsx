import { render, screen, waitFor } from '@testing-library/react'
import '@testing-library/jest-dom'
import { MemoryRouter, Route, Routes } from 'react-router-dom'
import VideoDetails from './VideoDetails'
import { VideoPreviewData } from '../model/VideoPreviewData'
import useFetchVideoDetails from '@/hooks/useFetchVideoDetails'

jest.mock('@/utils/Env', () => ({
  getEnv: () => ({
    MEDIA_BASE_URL: 'http://mockedurl.com',
  }),
}))

jest.mock('@/hooks/useFetchVideoDetails')

const mockVideo: VideoPreviewData = {
  videoId: 'test-video-id',
  videoFileName: 'test-video.mp4',
  thumbnailFileName: 'test-thumbnail.jpg',
  title: 'Test Video',
  username: 'TestUser',
  meta: {
    tags: [{ tagName: 'Tag1' }, { tagName: 'Tag2' }],
    categories: [{ categoryName: 'Category1' }, { categoryName: 'Category2' }],
    comments: [
      { videoId: '1', username: 'User1', text: 'Comment1' },
      { videoId: '2', username: 'User2', text: 'Comment2' },
    ],
  },
}

describe('VideoDetails Component', () => {
  beforeEach(() => {
    ;(useFetchVideoDetails as jest.Mock).mockReturnValue({
      video: mockVideo,
      loading: false,
      error: null,
    })
    jest.spyOn(console, 'log').mockImplementation(() => {}) // Mock console.log
  })

  afterEach(() => {
    jest.restoreAllMocks() // Restore console.log
  })

  it('renders video details correctly', async () => {
    render(
      <MemoryRouter initialEntries={['/video-details?id=test-video-id']}>
        <Routes>
          <Route path="/video-details" element={<VideoDetails />} />
        </Routes>
      </MemoryRouter>
    )

    await waitFor(() =>
      expect(screen.getByText('Test Video')).toBeInTheDocument()
    )
    expect(screen.getByText('Tag1')).toBeInTheDocument()
    expect(screen.getByText('Category1')).toBeInTheDocument()
    expect(screen.getByText('Comment1')).toBeInTheDocument()
  })
})
