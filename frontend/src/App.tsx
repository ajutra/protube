import { useState, useEffect } from "react";
import VideoCard from './components/VideoCard';
import VideoDetails from './components/VideoDetails';
import { VideoPreviewData } from './model/VideoTypes'; 
import { getEnv } from './utils/Env';
import './App.css';
import './components/styles/VideoCard.css';

function App() {
  const [videos, setVideos] = useState<VideoPreviewData[]>([]);
  const [selectedVideo, setSelectedVideo] = useState<VideoPreviewData | null>(null);
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
      {selectedVideo ? (
        <VideoDetails video={selectedVideo} onBack={() => setSelectedVideo(null)} />
      ) : (
        <div className='w-100 ms-5 mt-4'>
          <header>
            <h1 className="text-start">
              Protube
            </h1>
          </header>
          <div className="container">
            {isLoading ? (
              <p>Loading...</p>
            ) : videos.length > 0 ? (
              <div className="row">
                {videos.map((video, index) => (
                  <div key={index} className="h-100 col-md-4 col-lg-3 mb-4">
                    <VideoCard
                      video={video}
                      onClick={() => setSelectedVideo(video)}
                    />
                  </div>
                ))}
              </div>
            ) : (
              <p>No videos found</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
