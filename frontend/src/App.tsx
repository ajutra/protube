import './App.css';
import './components/styles/VideoCard.css'
import VideoCard from './components/VideoCard';
import { VideoPreviewData } from './model/VideoPreviewData';
import { getEnv } from './utils/Env';
import { useEffect, useState } from 'react';

function App() {
  const [videos, setVideos] = useState<VideoPreviewData[]>([]);

  useEffect(() => {
    fetch(getEnv().API_BASE_URL + '/videos')
      .then(response => response.json())
      .then(data => setVideos(data))
      .catch(error => console.error("Error fetching videos: ", error));
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <div className="container">
          {videos.length > 0 ? (
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
            <p>Loading...</p>
          )}
        </div>
      </header>
    </div>
  );
}

export default App;
