import React from 'react'
import { Link, useLocation } from 'react-router-dom'
import { AppRoutes } from '@/enums/AppRoutes'
import useFetchVideoDetails from '@/hooks/useFetchVideoDetails'
import Tags from '@/components/Tags'
import Categories from '@/components/Categories'
import Comments from '@/components/Comments'
import { getEnv } from '@/utils/Env'

const useQuery = () => new URLSearchParams(useLocation().search)

const VideoDetails: React.FC = () => {
  const query = useQuery()
  const videoId = query.get('id')
  const { video, loading, error } = useFetchVideoDetails(videoId)

  const handleError = (
    event: React.SyntheticEvent<HTMLVideoElement, Event>
  ) => {
    console.error('Video Error:', event)
  }

  if (loading) return <p>Loading...</p>
  if (error) return <p>{error.message}</p>
  if (!video) return <p>Video not found</p>

  const videoURL = `${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`

  return (
    <div className="App">
      <div className="container pt-4">
        <div className="d-flex justify-content-start mb-2">
          <Link to={AppRoutes.HOME}>
            <button className="btn btn-outline-light">←</button>
          </Link>
        </div>
        <div className="row justify-content-center mt-5">
          <div className="col-lg-8">
            <video
              data-testid="video-element"
              controls
              src={videoURL}
              className="w-100 video-responsive"
              onError={handleError}
            />
          </div>
        </div>
        <div className="details mt-3 text-center">
          <h2 className="bg-dark rounded p-3 text-white">{video.title}</h2>
          <Tags tags={video.meta?.tags || []} />
          <Categories categories={video.meta?.categories || []} />
          <Comments comments={video.meta?.comments || []} />
        </div>
      </div>
    </div>
  )
}

export default VideoDetails
