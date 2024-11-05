import React, { useState } from 'react';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';

const VideoCard: React.FC<VideoPreviewData> = ({ videoFileName, thumbnailFileName, title, username }) => {
  const [isHovered, setIsHovered] = useState(false);

  const handleMouseEnter = () => setIsHovered(true);
  const handleMouseLeave = () => setIsHovered(false);

  return (
    <div
      className="card video-card text-bg-secondary hover-zoom"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      style={{ cursor: 'pointer' }}
    >
      {isHovered ? (
        <video
          src={`${getEnv().MEDIA_BASE_URL}/${videoFileName}`}
          className="video-card-media"
          autoPlay
          loop
          muted
        />
      ) : (
        <img
          src={`${getEnv().MEDIA_BASE_URL}/${thumbnailFileName}`}
          alt={thumbnailFileName}
          className="video-card-media"
        />
      )}
      <div className="card-body text-start">
        <h2 className="fw-bold text-truncate video-card-title">{title}</h2>
        <p className="fs-6 text-truncate">{username}</p>
      </div>
    </div>
  );
};

export default VideoCard;
