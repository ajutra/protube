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
      <div className='w-100 ms-5 mt-4'>
        <header>
          <h1 className="text-start">
            Protube
          </h1>
        </header>
      </div>
        <div className="container">
          {videos.length > 0 ? (
            <div className="row">
              {videos.map((video, index) => (
                <div key={index} className="h-100 col-md-4 col-lg-3 mb-4">
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
    </div>
  );
}

export default App;
