import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
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
    render(<VideoDetails />);

    const videoElement = screen.getByTestId('video-element');
    const titleElement = screen.getByText('Test Video');
    
    expect(videoElement).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4');
    expect(titleElement).toBeInTheDocument();
  });

  it('displays error message if no video data is found', () => {
    localStorage.removeItem('selectedVideo');

    render(<VideoDetails />);

    expect(screen.getByText('No video data found.')).toBeInTheDocument();
  });
});
