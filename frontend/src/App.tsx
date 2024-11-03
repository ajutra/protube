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
        {videos.length > 0 ? (
          <VideoCard
            videoFileName={videos[0].videoFileName}
            thumbnailFileName={videos[0].thumbnailFileName}
            title={videos[0].title}
            username={videos[0].username}
          />
        ) : (
          <p>Loading...</p>
        )}
      </header>
    </div>
  );
}

export default App;
