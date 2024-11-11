// src/components/__tests__/VideoCard.test.tsx
import { render, fireEvent, waitFor } from '@testing-library/react';
import "@testing-library/jest-dom";
import VideoCard from '../VideoCard';
import { VideoPreviewData } from '../../model/VideoPreviewData';

jest.mock('../../utils/Env', () => ({
  getEnv: () => ({
    MEDIA_BASE_URL: 'http://mockedurl.com'
  })
}));

const mockVideoData: VideoPreviewData = {
  videoFileName: 'test-video.mp4',
  thumbnailFileName: 'test-thumbnail.jpg',
  title: 'Test Video',
  username: 'testuser'
};

test('renders VideoCard and handles hover', async () => {
  const { getByAltText, getByRole } = render(
    <VideoCard {...mockVideoData} />
  );

  // Verificar si la miniatura se renderiza inicialmente
  const thumbnail = getByAltText('test-thumbnail.jpg');
  expect(thumbnail).toHaveAttribute('src', 'http://mockedurl.com/test-thumbnail.jpg');

  // Simular mouse enter
  fireEvent.mouseEnter(thumbnail);

  // Esperar a que el video se renderice después del hover
  await waitFor(() => {
    const video = getByRole('video');
    expect(video).toBeInTheDocument();
    expect(video).toHaveAttribute('src', 'http://mockedurl.com/test-video.mp4');
    expect(video).toHaveAttribute('autoPlay');
    expect(video).toHaveAttribute('loop');
  });

  // Simular mouse leave
  fireEvent.mouseLeave(thumbnail);

  // Verificar si la miniatura se renderiza nuevamente
  expect(thumbnail).toHaveAttribute('src', 'http://mockedurl.com/test-thumbnail.jpg');
});