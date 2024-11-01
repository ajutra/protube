// App.tsx
import React, { useState, useEffect } from 'react';
import './App.css';
import './components/styles/VideoCard.css'
import VideoCard from './components/VideoCard';

interface Video {
  videoFileName: string;
  thumbnailFileName: string;
  title: string;
  username: string;
}

function App() {
  const [videos, setVideos] = useState<Video[]>([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/videos')
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
