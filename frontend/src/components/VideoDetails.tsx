import React from 'react';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import 'bootstrap/dist/css/bootstrap.min.css';

interface VideoDetailsProps {
  video: VideoPreviewData;
  onBack: () => void;
  buttonColor?: string;
}

const VideoDetails: React.FC<VideoDetailsProps> = ({ video, onBack }) => {
  return (
    <div className="container pt-4"> {}
      <div className="d-flex justify-content-start mb-2"> {}
        <button
          className="btn btn-primary"
          onClick={onBack}
          style={{ backgroundColor: 'transparent', color: '#ffffff' }}  
        >
          ←
        </button>
      </div>
      <div className="row mt-5"> {}
        <div className="col">
          <video
            data-testid="video-element"
            controls
            src={`${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`}
            className="w-100"
            style={{ maxWidth: '90vw', height: 'auto' }} 
          />
        </div>
      </div>
      <div className="details mt-3">
        <h2>{video.title}</h2>
      </div>
    </div>
  );
};

export default VideoDetails;
