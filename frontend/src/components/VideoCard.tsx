// VideoCard.tsx
import React, { useState } from 'react';

interface VideoCardProps {
  videoFileName: string;
  thumbnailFileName: string;
  title: string;
  username: string;
}

const VideoCard: React.FC<VideoCardProps> = ({ videoFileName, thumbnailFileName, title, username }) => {
  const [isPlaying, setIsPlaying] = useState(false);

  const handleVideoClick = () => {
    setIsPlaying(true);
  };

  return (
    <div>
      {!isPlaying ? (
        <div className="video-card" onClick={handleVideoClick}>
          <div className="video-card-content">
            <img
              src={`http://localhost:8080/media/${thumbnailFileName}`}
              alt={thumbnailFileName}
              className="video-card-thumbnail"
            />
            <div className="video-card-info">
              <h2>{title}</h2>
              <p>{username}</p>
            </div>
          </div>
        </div>
      ) : (
        <iframe
          width="560"
          height="315"
          src={`http://localhost:8080/media/${videoFileName}`}
          title={title}
        ></iframe>
      )}
    </div>
  );
};

export default VideoCard;
