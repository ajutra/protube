import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useState, useEffect } from 'react';
import VideoCard from './components/VideoCard';
import VideoDetails from './components/VideoDetails';
import { VideoPreviewData } from './model/VideoPreviewData';
import { getEnv } from './utils/Env';
import './App.css';
import './components/styles/VideoCard.css';

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
    <Router>
      <Routes>
        <Route path="/" element={
          <div className="App">
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
                        <VideoCard video={video} />
                      </div>
                    ))}
                  </div>
                ) : (
                  <p>No videos found</p>
                )}
              </div>
            </div>
          </div>
        } />
        <Route path="/video-details" element={<VideoDetails />} />
      </Routes>
    </Router>
  );
}

export default App;
