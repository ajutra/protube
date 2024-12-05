import VideoPreview from '@/components/VideoPreview'
import { getEnv } from '../utils/Env'
import useFetchAllVideos from '@/hooks/useFetchAllVideos'
import Spinner from '@/components/Spinner'
import { Link } from 'react-router-dom'
import { AppRoutes } from '@/enums/AppRoutes'

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
        <div className="flex flex-wrap p-4">
          {videos.map((video, index) => (
            <div
              key={index}
              className="w-full p-4 sm:w-1/2 md:w-1/3 lg:w-1/4 xl:w-1/5 2xl:w-1/6"
            >
              <Link to={AppRoutes.VIDEO_DETAILS + '?id=' + video.videoId}>
                <VideoPreview
                  videoId={video.videoId}
                  videoFileName={video.videoFileName}
                  thumbnailFileName={video.thumbnailFileName}
                  title={video.title}
                  username={video.username}
                  likes={video.likes}
                  dislikes={video.dislikes}
                />
              </Link>
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
