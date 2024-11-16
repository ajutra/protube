import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';
import './components/styles/VideoCard.css';
import VideoCard from './components/VideoCard';
import VideoDetails from './components/VideoDetails';
import { VideoPreviewData } from './model/VideoPreviewData';
import { getEnv } from './utils/Env';
import { AppRoutes } from './enums/AppRoutes';

function App() {
  const [videos, setVideos] = useState<VideoPreviewData[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch(getEnv().API_BASE_URL + '/videos')
      .then((response) => response.json())
      .then((data) => {
        setVideos(data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching videos: ', error);
        setIsLoading(false);
      });
  }, []);

  const handleVideoClick = () => {
    navigate(AppRoutes.VIDEO_DETAILS);
  };

  return (
    <div className="App p-5">
      <div className="container mt-5">
        {isLoading ? (
          <p>Loading...</p>
        ) : videos.length > 0 ? (
          <div className="row">
            {videos.map((video, index) => (
              <div key={index} className="h-100 col-md-4 col-lg-3 mb-4">
                <VideoCard video={video} onClick={handleVideoClick} />
              </div>
            ))}
          </div>
        ) : (
          <p>No videos found</p>
        )}
      </div>
    </div>
  );
}

export default App;