import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import 'bootstrap/dist/css/bootstrap.min.css';
import Tags from './Tags';
import Categories from './Categories';
import Comments from './Comments';
import { AppRoutes } from '../enums/AppRoutes';

const VideoDetails: React.FC = () => {
  const [video, setVideo] = useState<VideoPreviewData | null>(null);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const videoData = localStorage.getItem('selectedVideo');
    if (videoData) {
      setVideo(JSON.parse(videoData));
    } else {
      setError('No video data found.');
    }
  }, []);

  const handleError = (event: React.SyntheticEvent<HTMLVideoElement, Event>) => {
    setError('There was an error loading the video. Please try again later.');
    console.error('Video Error:', event);
  };

  if (error) return <p>{error}</p>;
  if (!video) return <p>Loading...</p>;

  const videoURL = `${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`;
  console.log('Video URL:', videoURL);

  const handleBackClick = () => {
    navigate(AppRoutes.HOME); 
  };

  return (
    <div className="App">
      <div className="container pt-4">
        <div className="d-flex justify-content-start mb-2">
          <button className="btn btn-outline-light" onClick={handleBackClick}>
            ←
          </button>
        </div>
        <div className="row mt-5 justify-content-center">
          <div className="col-lg-8">
            <video
              data-testid="video-element"
              controls
              src={videoURL}
              className="w-100 video-responsive"
              onError={handleError}
            />
          </div>
        </div>
        <div className="details mt-3 text-center">
          <h2 className="text-white bg-dark p-3 rounded">{video.title}</h2>
          <Tags tags={video.meta.tags} />
          <Categories categories={video.meta.categories} />
          <Comments comments={video.meta.comments} />
        </div>
      </div>
    </div>
  );
};

export default VideoDetails;