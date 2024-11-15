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
  username: 'TestUser',
  meta: {
    tags: [{ tagName: 'Tag1' }, { tagName: 'Tag2' }],
    categories: [{ categoryName: 'Category1' }, { categoryName: 'Category2' }],
    comments: [
      { videoId: '1', username: 'User1', text: 'Comment1' },
      { videoId: '2', username: 'User2', text: 'Comment2' },
    ],
  },
};

describe('VideoDetails Component', () => {
  it('renders video details correctly', () => {
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);

    const videoElement = screen.getByTestId('video-element');
    const titleElement = screen.getByText('Test Video');
    
    expect(videoElement).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4');
    expect(titleElement).toBeTruthy();
  });

  it('calls onBack when the back button is clicked', () => {
    const onBackMock = jest.fn();
    render(<VideoDetails video={mockVideo} onBack={onBackMock} />);
    const backButton = screen.getByRole('button', { name: '←' });

    fireEvent.click(backButton);
    expect(onBackMock).toHaveBeenCalledTimes(1);
  });

  test('renders tags', () => {
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);
    expect(screen.getByText('Tag1')).toBeTruthy();
    expect(screen.getByText('Tag2')).toBeTruthy();
  });

  test('renders categories', () => {
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);
    expect(screen.getByText('Category1')).toBeTruthy();
    expect(screen.getByText('Category2')).toBeTruthy();
  });

  test('renders comments', () => {
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);
    expect(screen.getByText('User1')).toBeTruthy();
    expect(screen.getByText('Comment1')).toBeTruthy();
    expect(screen.getByText('User2')).toBeTruthy();
    expect(screen.getByText('Comment2')).toBeTruthy();
  });

  test('does not render videoId in tags, categories, or comments', () => {
    render(<VideoDetails video={mockVideo} onBack={jest.fn()} />);
    expect(screen.queryByText('1')).toBeNull();
    expect(screen.queryByText('2')).toBeNull();
  });

  test('renders no tags available message', () => {
    const videoWithoutTags = { ...mockVideo, meta: { ...mockVideo.meta, tags: [] } };
    render(<VideoDetails video={videoWithoutTags} onBack={jest.fn()} />);
    expect(screen.queryByText('No tags available')).not.toBeNull();
  });

  test('renders no categories available message', () => {
    const videoWithoutCategories = { ...mockVideo, meta: { ...mockVideo.meta, categories: [] } };
    render(<VideoDetails video={videoWithoutCategories} onBack={jest.fn()} />);
    expect(screen.queryByText('No categories available')).not.toBeNull();
  });

  test('renders no comments available message', () => {
    const videoWithoutComments = { ...mockVideo, meta: { ...mockVideo.meta, comments: [] } };
    render(<VideoDetails video={videoWithoutComments} onBack={jest.fn()} />);
    expect(screen.queryByText('No comments available')).not.toBeNull();
  });
});