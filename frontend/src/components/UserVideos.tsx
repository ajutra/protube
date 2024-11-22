// src/components/UserVideos.tsx
import React from 'react';
import { Link } from 'react-router-dom';
import { AppRoutes } from '@/enums/AppRoutes';
import VideoPreview from '@/components/VideoPreview';
import useFetchUserVideos from '@/hooks/useFetchUserVideos';
import { VideoPreviewData } from '@/model/VideoPreviewData';

interface UserVideosProps {
  username: string;
}

const UserVideos: React.FC<UserVideosProps> = ({ username }) => {
  const { videos, loading, error } = useFetchUserVideos(username);

  if (loading) {
    return <p>Loading videos...</p>;
  }

  if (error) {
    return <p>Error loading videos: {error.message}</p>;
  }

  if (videos.length === 0) {
    return <p>No videos yet</p>;
  }

  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {videos.map((video: VideoPreviewData) => (
        <div key={video.videoId} className='mt-4'>
          <Link to={AppRoutes.VIDEO_DETAILS + '?id=' + video.videoId}>
            <VideoPreview
              videoId={video.videoId}
              videoFileName={video.videoFileName}
              thumbnailFileName={video.thumbnailFileName}
              title={video.title}
              username={video.username}
            />
          </Link>
        </div>
      ))}
    </div>
  );
};

export default UserVideos;