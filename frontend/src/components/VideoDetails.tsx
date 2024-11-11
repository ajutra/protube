import React, { useEffect, useState } from 'react';
import { VideoPreviewData } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import 'bootstrap/dist/css/bootstrap.min.css';

const VideoDetails: React.FC = () => {
  const [video, setVideo] = useState<VideoPreviewData | null>(null);
  const [error, setError] = useState<string | null>(null);

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
  console.log("Video URL:", videoURL);

  return (
    <div className="App"> {}
      <div className="container pt-4">
        <div className="row mt-5 justify-content-center"> {}
          <div className="col-lg-8"> {}
            <video
              data-testid="video-element"
              controls
              src={videoURL}
              className="w-100"
              style={{ maxWidth: '90vw', height: 'auto' }}
              onError={handleError}
            />
          </div>
        </div>
        <div className="details mt-3 text-center"> {}
          <h2>{video.title}</h2>
        </div>
      </div>
    </div>
  );
};

export default VideoDetails;
