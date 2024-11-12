import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom'; 
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import VideoDetails from '../VideoDetails';
import { VideoPreviewData } from '../../model/VideoPreviewData';

jest.mock('../../utils/Env', () => ({
  getEnv: () => ({
    MEDIA_BASE_URL: 'http://mockedurl.com',
  }),
}));

const mockVideo: VideoPreviewData = {
  videoFileName: 'test-video.mp4',
  thumbnailFileName: 'test-thumbnail.jpg',
  title: 'Test Video',
  username: 'Test User',
};

describe('VideoDetails Component', () => {
  beforeEach(() => {
    localStorage.setItem('selectedVideo', JSON.stringify(mockVideo));
  });

  afterEach(() => {
    localStorage.removeItem('selectedVideo');
  });

  it('renders video details correctly', () => {
    render(
      <MemoryRouter initialEntries={['/video-details']}>
        <Routes>
          <Route path="/video-details" element={<VideoDetails />} />
        </Routes>
      </MemoryRouter>
    );

    const videoElement = screen.getByTestId('video-element');
    const titleElement = screen.getByText('Test Video');
    
    expect(videoElement).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4');
    expect(titleElement).toBeInTheDocument();
  });

  it('displays error message if no video data is found', () => {
    localStorage.removeItem('selectedVideo');

    render(
      <MemoryRouter initialEntries={['/video-details']}>
        <Routes>
          <Route path="/video-details" element={<VideoDetails />} />
        </Routes>
      </MemoryRouter>
    );

    expect(screen.getByText('No video data found.')).toBeInTheDocument();
  });

  it('navigates back when the back button is clicked', () => {
    render(
      <MemoryRouter initialEntries={['/video-details']}>
        <Routes>
          <Route path="/video-details" element={<VideoDetails />} />
          <Route path="/" element={<div>Home Page</div>} />
        </Routes>
      </MemoryRouter>
    );

    const backButton = screen.getByText('←');
    fireEvent.click(backButton);

    expect(screen.getByText('Home Page')).toBeInTheDocument();
  });
});