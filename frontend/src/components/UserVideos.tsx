import React from 'react'
import useFetchUserVideos from '@/hooks/useFetchUserVideos'
import { VideoPreviewData } from '@/model/VideoPreviewData'
import Spinner from '@/components/Spinner'
import VideoPreviewProfile from './VideoPreviewProfile'

interface UserVideosProps {
  username: string
}

const UserVideos: React.FC<UserVideosProps> = ({ username }) => {
  const { videos, loading, error, refetch } = useFetchUserVideos(username)

  const refetchVideos = () => {
    refetch()
  }

  if (loading) {
    return (
      <div className="mt-10 flex items-center justify-center">
        <Spinner />
      </div>
    )
  }

  if (error) {
    return <p>Error loading videos: {error.message}</p>
  }

  if (videos.length === 0) {
    return <p>No videos yet</p>
  }

  return (
    <div className="mt-4 max-w-screen-xl flex-row space-y-5">
      {videos.map((video: VideoPreviewData) => (
        <div key={video.videoId} className="w-full">
          <VideoPreviewProfile
            videoId={video.videoId}
            videoFileName={video.videoFileName}
            thumbnailFileName={video.thumbnailFileName}
            title={video.title}
            username={video.username}
            meta={video.meta}
            onDelete={refetchVideos}
            onSavedEdit={refetchVideos}
          />
        </div>
      ))}
    </div>
  )
}

export default UserVideos
