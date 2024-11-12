import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import { AppRoutes } from '../enums/AppRoutes';

interface VideoCardProps {
  video: VideoPreviewData;
  onClick: () => void;
}

const VideoCard: React.FC<VideoCardProps> = ({ video }) => {
  const [isHovered, setIsHovered] = useState(false);
  const [hoverTimeout, setHoverTimeout] = useState<NodeJS.Timeout | null>(null);
  const navigate = useNavigate();

  const handleMouseEnter = () => {
    const timeout = setTimeout(() => {
      setIsHovered(true);
    }, 500);
    setHoverTimeout(timeout);
  };

  const handleMouseLeave = () => {
    if (hoverTimeout) {
      clearTimeout(hoverTimeout);
      setHoverTimeout(null);
    }
    setIsHovered(false);
  };

  const handleClick = () => {
    localStorage.setItem('selectedVideo', JSON.stringify(video)); 
    navigate(AppRoutes.VIDEO_DETAILS);
  };

  return (
    <div
      className="card video-card text-bg-secondary hover-zoom"
      onClick={handleClick}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      style={{ cursor: 'pointer' }}
    >
      {isHovered ? (
        <video
          src={`${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`}
          className="video-card-media"
          autoPlay
          loop
          muted
        />
      ) : (
        <img
          src={`${getEnv().MEDIA_BASE_URL}/${video.thumbnailFileName}`}
          alt={video.thumbnailFileName}
          className="video-card-media"
        />
      )}
      <div className="card-body text-start">
        <h2 className="fw-bold text-truncate video-card-title">{video.title}</h2>
        <p className="fs-6 text-truncate">{video.username}</p>
      </div>
    </div>
  );
};

export default VideoCard;
