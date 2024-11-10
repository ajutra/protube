import { render, screen, fireEvent } from '@testing-library/react';
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
  it('renders video details correctly', () => {
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);

    const videoElement = screen.getByTestId('video-element');
    const titleElement = screen.getByText('Test Video');
    
    expect(videoElement).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4');
    expect(titleElement).toBeInTheDocument();
  });

  it('calls onBack when the back button is clicked', () => {
    const onBackMock = jest.fn();
    render(<VideoDetails video={mockVideo} onBack={onBackMock} />);
    const backButton = screen.getByRole('button', { name: '←' });

    fireEvent.click(backButton);
    expect(onBackMock).toHaveBeenCalledTimes(1);
  });
});
