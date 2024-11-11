import React from 'react';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import 'bootstrap/dist/css/bootstrap.min.css';
import Tags from './Tags';
import Categories from './Categories';
import Comments from './Comments';

interface VideoDetailsProps {
  video: VideoPreviewData;
  onBack: () => void;
  buttonColor?: string;
}

const VideoDetails: React.FC<VideoDetailsProps> = ({ video, onBack }) => {
  return (
    <div className="container pt-4">
      <div className="d-flex justify-content-start mb-2">
        <button
          className="btn btn-outline-light"
          onClick={onBack}
        >
          ←
        </button>
      </div>
      <div className="row mt-5">
        <div className="col">
          <video
            data-testid="video-element"
            controls
            src={`${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`}
            className="w-100 mw-100 h-auto"
          />
        </div>
      </div>
      <div className="details mt-3">
        <h2 className="text-white bg-dark p-3 rounded">{video.title}</h2>
        <Tags tags={video.meta.tags} />
        <Categories categories={video.meta.categories} />
        <Comments comments={video.meta.comments} />
      </div>
    </div>
  );
};

export default VideoDetails;