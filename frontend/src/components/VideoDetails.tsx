import React from 'react';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import 'bootstrap/dist/css/bootstrap.min.css';

interface VideoDetailsProps {
  video: VideoPreviewData;
  onBack: () => void;
  buttonColor?: string;
}

const VideoDetails: React.FC<VideoDetailsProps> = ({ video, onBack, buttonColor }) => {
  const buttonStyle: React.CSSProperties = {
    color: buttonColor || '#ffffff',
    background: 'none',
    border: 'none',
    fontSize: '36px',
    cursor: 'pointer',
    position: 'absolute',
    top: '20px',
    left: '20px',
    zIndex: 1
  };

  const containerStyle: React.CSSProperties = {
    position: 'relative',
    paddingTop: '70px'
  };

  const videoStyle: React.CSSProperties = {
    width: '100%',
    maxWidth: '1000px', // Asegura un tamaño máximo consistente para los videos
    height: 'auto'
  };

  return (
    <div className="container" style={containerStyle}>
      <div style={{ position: 'absolute', top: 0, left: 0 }}>
        <button
          className="btn btn-primary back-button"
          onClick={onBack}
          style={buttonStyle}
        >
          ←
        </button>
      </div>
      <div className="row" style={{ position: 'relative' }}>
        <div className="col">
          <video
            data-testid="video-element"
            controls
            src={`${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`}
            style={videoStyle} // Aplicamos el estilo del video
          />
        </div>
      </div>
      <div className="details">
        <h2>{video.title}</h2>
      </div>
    </div>
  );
};

export default VideoDetails;
