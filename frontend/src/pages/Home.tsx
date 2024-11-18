import VideoPreview from '@/components/VideoPreview'
import { getEnv } from '../utils/Env'
import useFetchAllVideos from '@/hooks/useFetchAllVideos'
import Spinner from '@/components/Spinner'

function Home() {
  const { videos, loading, error } = useFetchAllVideos(
    getEnv().API_BASE_URL + '/videos'
  )

  return (
    <div className="mt-4">
      {loading ? (
        <div className="flex h-screen items-center justify-center">
          <Spinner />
        </div>
      ) : error ? (
        <p>Error fetching videos: {error.message}</p>
      ) : videos.length > 0 ? (
        <div className="flex flex-wrap">
          {videos.map((video, index) => (
            <div key={index} className="w-full p-4 sm:w-1/2 md:w-1/3 lg:w-1/4">
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
  )
}

export default Home
