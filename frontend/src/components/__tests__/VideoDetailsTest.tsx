import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import VideoDetails from '../VideoDetails';
import { VideoPreviewData } from '../../model/VideoPreviewData';
import { getEnv } from '../../utils/Env';

// Mocking getEnv to provide a consistent MEDIA_BASE_URL
jest.mock('../../utils/Env', () => ({
  getEnv: () => ({
    MEDIA_BASE_URL: 'http://mockedurl.com',
  }),
}));

// Creating a mock video data object to use in the tests
const mockVideo: VideoPreviewData = {
  videoFileName: 'test-video.mp4',
  thumbnailFileName: 'test-thumbnail.jpg',
  title: 'Test Video',
  username: 'Test User',
};

describe('VideoDetails Component', () => {
  // Test to ensure that the VideoDetails component renders the video details correctly
  it('renders video details correctly', () => {
    // Rendering the VideoDetails component with mock data and a mock onBack function
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);

    // Selecting the video element by its test ID and the title element by its text content
    const videoElement = screen.getByTestId('video-element');
    const titleElement = screen.getByText('Test Video');
    
    // Assertions to check if the video element has the correct src attribute
    // and the title element is present in the document
    expect(videoElement).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4');
    expect(titleElement).toBeInTheDocument();
  });

  // Test to ensure that the onBack function is called when the back button is clicked
  it('calls onBack when the back button is clicked', () => {
    // Creating a mock function for onBack
    const onBackMock = jest.fn();

    // Rendering the VideoDetails component with mock data and the mock onBack function
    render(<VideoDetails video={mockVideo} onBack={onBackMock} />);

    // Selecting the back button by its role and name
    const backButton = screen.getByRole('button', { name: '←' });

    // Simulating a click event on the back button
    fireEvent.click(backButton);

    // Assertion to check if the mock onBack function was called once
    expect(onBackMock).toHaveBeenCalledTimes(1);
  });
});
