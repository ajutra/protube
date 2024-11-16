import VideoPreview from '@/components/VideoPreview'
import Header from '../components/Header'
import { VideoPreviewData } from '../model/VideoPreviewData'
import { getEnv } from '../utils/Env'
import { useEffect, useState } from 'react'

function Home() {
  const [videos, setVideos] = useState<VideoPreviewData[]>([])
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    fetch(getEnv().API_BASE_URL + '/videos')
      .then((response) => response.json())
      .then((data) => {
        setVideos(data)
        setIsLoading(false)
      })
      .catch((error) => {
        console.error('Error fetching videos: ', error)
        setIsLoading(false)
      })
  }, [])

  return (
    <div className="p-5">
      <Header />
      <div>
        {isLoading ? (
          <p>Loading...</p>
        ) : videos.length > 0 ? (
          <div className="flex flex-wrap">
            {videos.map((video, index) => (
              <div
                key={index}
                className="w-full p-4 sm:w-1/2 md:w-1/3 lg:w-1/4"
              >
                <VideoPreview
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
    </div>
  )
}

export default Home
