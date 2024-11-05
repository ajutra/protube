import './App.css';
import './components/styles/VideoCard.css';
import VideoCard from './components/VideoCard';
import { VideoPreviewData } from './model/VideoPreviewData';
import { getEnv } from './utils/Env';
import { useEffect, useState } from 'react';

function App() {
  const [videos, setVideos] = useState<VideoPreviewData[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetch(getEnv().API_BASE_URL + '/videos')
      .then(response => response.json())
      .then(data => {
        setVideos(data);
        setIsLoading(false);
      })
      .catch(error => {
        console.error("Error fetching videos: ", error);
        setIsLoading(false);
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <div className="container">
          {isLoading ? (
            <p>Loading...</p>
          ) : videos.length > 0 ? (
            <div className="row">
              {videos.map((video, index) => (
                <div key={index} className="col-md-4 col-lg-3 mb-4">
                  <VideoCard
                    videoFileName={video.videoFileName}
                    thumbnailFileName={video.thumbnailFileName}
                    title={video.title}
                    username={video.username}
                  />
                </div>
              ))}
            </div>
          ) : (
            <p>No videos found</p>
          )}
        </div>
      </header>
    </div>
  );
}

export default App;
