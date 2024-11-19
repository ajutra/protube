import React from 'react'
import { useLocation } from 'react-router-dom'
import useFetchVideoDetails from '@/hooks/useFetchVideoDetails'
import Tags from '@/components/Tags'
import Categories from '@/components/Categories'
import Comments from '@/components/Comments'
import { getEnv } from '@/utils/Env'
import { Card, CardContent, CardTitle } from '@/components/ui/card'

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
    <div className="flex min-h-screen flex-col items-center justify-center p-4 pt-24">
      <Card className="mt-8 w-full max-w-7xl px-4">
        <CardContent className="mt-4">
          <div className="mb-8 flex justify-center">
            <div className="w-full">
              <video
                data-testid="video-element"
                controls
                src={videoURL}
                className="w-full rounded-lg shadow-lg"
                onError={handleError}
              />
            </div>
          </div>
          <CardTitle className="mb-6 text-center text-3xl font-extrabold">
            {video.title}
          </CardTitle>
          <div className="text-center">
            <div className="mb-6">
              <Tags tags={video.meta?.tags || []} />
            </div>
            <div className="mb-6">
              <Categories categories={video.meta?.categories || []} />
            </div>
            <div className="mb-6">
              <Comments comments={video.meta?.comments || []} />
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}

export default VideoDetails
