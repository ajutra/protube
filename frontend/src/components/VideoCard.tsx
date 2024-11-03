import React, { useState } from 'react';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';

const VideoCard: React.FC<VideoPreviewData> = ({ videoFileName, thumbnailFileName, title, username }) => {
  const [isHovered, setIsHovered] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);

  const handleVideoClick = () => {
    setIsPlaying(true);
  };

  const handleMouseEnter = () => setIsHovered(true);
  const handleMouseLeave = () => setIsHovered(false);

  return (
    <div className="container d-flex justify-content-center align-items-center">
      {!isPlaying ? (
        <div
          className="w-25"
          onClick={handleVideoClick}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
          style={{ cursor: 'pointer' }}
        >
          <div className="card text-bg-secondary hover-zoom">
            {isHovered ? (
              <video
                src={`${getEnv().MEDIA_BASE_URL}/${videoFileName}`}
                className="img-fluid rounded"
                autoPlay
                loop
                muted
              />
            ) : (
              <img
                src={`${getEnv().MEDIA_BASE_URL}/${thumbnailFileName}`}
                alt={thumbnailFileName}
                className="img-fluid rounded"
              />
            )}
            <div className="card-body text-start">
              <h2 className="fw-bold">{title}</h2>
              <p className="fs-6">{username}</p>
            </div>
          </div>
        </div>
      ) : (
        <div className="container">
          <iframe
            className="w-100"
            src={`${getEnv().MEDIA_BASE_URL}/${videoFileName}`}
            title={title}
          ></iframe>
        </div>
      )}
    </div>
  );
};

export default VideoCard;
