import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import useFetchVideoDetails from '@/hooks/useFetchVideoDetails'
import Tags from '@/components/Tags'
import Categories from '@/components/Categories'
import Comments from '@/components/Comments'
import { getEnv } from '@/utils/Env'
import {
  Card,
  CardContent,
  CardDescription,
  CardTitle,
} from '@/components/ui/card'
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from '@/components/ui/collapsible'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import Spinner from '@/components/Spinner'
import processDescription from '@/utils/processDescription'
import CommentAndVideoActions from '@/components/CommentAndVideoActions'
import { useAuth } from '@/context/AuthContext'
import useDeleteVideo from '@/hooks/useDeleteVideo'
import EditVideoForm from '@/components/EditVideoForm'
import { Dialog, DialogContent, DialogOverlay } from '@/components/ui/dialog'
import { LikeAndDislikeButtons } from '@/components/LikeAndDislikeButtons'
import { UserAcceptance } from '@/components/UserAcceptance'

const useQuery = () => new URLSearchParams(useLocation().search)

const VideoDetails: React.FC = () => {
  const query = useQuery()
  const { username } = useAuth()
  const videoId = query.get('id')
  const { video, loading, error, refetch } = useFetchVideoDetails(videoId)
  const [openDescription, setOpenDescription] = React.useState(false)
  const { processedDescription, lineCount } = processDescription(
    video?.meta?.description || ''
  )
  const { isLoading, showErrorDeletingVideo, handleOnDeleteVideo } =
    useDeleteVideo(video?.videoId, true)
  const [isEditing, setIsEditing] = useState(false)

  const handleError = (
    event: React.SyntheticEvent<HTMLVideoElement, Event>
  ) => {
    console.error('Video Error:', event)
  }

  useEffect(() => {
    setOpenDescription(false)
  }, [videoId])

  const handleEditClick = () => {
    setIsEditing(true)
  }

  const handleSave = () => {
    setIsEditing(false)
    refetch()
  }

  const handleCancel = () => {
    setIsEditing(false)
  }

  if (loading || isLoading)
    return (
      <div className="flex h-screen items-center justify-center">
        <Spinner />
      </div>
    )
  if (error) return <p>{error.message}</p>
  if (!video) return <p>Video not found</p>

  const videoURL = `${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`

  return (
    <div className="flex w-full justify-center">
      <Card className="mt-8 w-full max-w-screen-xl items-center border-none px-4 shadow-none">
        <CardContent className="mt-4 flex w-full flex-col items-center space-y-5">
          <video
            data-testid="video-element"
            controls
            src={videoURL}
            className="w-full rounded-xl"
            onError={handleError}
          />
          <CardTitle className="flex w-full justify-between text-left text-xl font-extrabold">
            <div className="w-auto">{video.title}</div>
            <div className="flex flex-col items-end space-x-2 space-y-2 lg:flex-row">
              <UserAcceptance
                likes={video.likes || 0}
                dislikes={video.dislikes || 0}
              />
              {username && (
                <>
                  <LikeAndDislikeButtons
                    videoLikes={video.likes || 0}
                    videoDislikes={video.dislikes || 0}
                    videoId={video.videoId}
                    username={username}
                    onActionComplete={refetch}
                  />
                  {username === video.username && (
                    <CommentAndVideoActions
                      buttonVariant="secondary"
                      openEditDialog={showErrorDeletingVideo}
                      editDialogTitle="Something went wrong!"
                      editDialogDescription="Video could not be edited. Please try again later."
                      deleteDialogTitle="Delete Video"
                      deleteDialogDescription="Are you sure you want to delete this video? This action cannot be undone."
                      onSelectEdit={handleEditClick}
                      onSelectDelete={handleOnDeleteVideo}
                    />
                  )}
                </>
              )}
            </div>
          </CardTitle>
          <Dialog open={isEditing} onOpenChange={setIsEditing}>
            <DialogOverlay />
            <DialogContent className="max-w-2xl p-6">
              <EditVideoForm
                video={{
                  videoId,
                  title: video.title,
                  description: video.meta?.description || '',
                }}
                onSave={handleSave}
                onCancel={handleCancel}
              />
            </DialogContent>
          </Dialog>
          <CardDescription className="flex w-full items-center space-x-2 text-secondary-foreground">
            <Avatar>
              <AvatarFallback className="bg-primary font-bold text-background dark:text-foreground">
                {video.username.charAt(0).toUpperCase()}
              </AvatarFallback>
            </Avatar>
            <span className="font-bold">{video.username}</span>
          </CardDescription>
          <Card className="w-full bg-secondary">
            <CardContent className="pb-6">
              <CardDescription className="text-secondary-foreground">
                <Collapsible onOpenChange={setOpenDescription}>
                  {lineCount < 4 ? (
                    <div className="mt-6 space-y-4">
                      {lineCount > 0 && processedDescription}
                      <Categories
                        badgeClassName="text-base font-bold"
                        categories={video.meta?.categories || []}
                      />
                      <Tags tags={video.meta?.tags || []} />
                    </div>
                  ) : openDescription ? (
                    <>
                      <CollapsibleContent className="mt-6 space-y-4">
                        {processedDescription}
                        <Categories
                          badgeClassName="text-base font-bold"
                          categories={video.meta?.categories || []}
                        />
                        <Tags tags={video.meta?.tags || []} />
                      </CollapsibleContent>
                      <CollapsibleTrigger className="mt-5 font-bold">
                        Show less
                      </CollapsibleTrigger>
                    </>
                  ) : (
                    <div className="mt-6 space-y-5">
                      <div className="line-clamp-4">{processedDescription}</div>
                      <CollapsibleTrigger className="font-bold">
                        Show more
                      </CollapsibleTrigger>
                    </div>
                  )}
                </Collapsible>
              </CardDescription>
            </CardContent>
          </Card>
          <Comments
            className="w-full"
            comments={video.meta?.comments || []}
            videoId={videoId || ''}
          />
        </CardContent>
      </Card>
    </div>
  )
}

export default VideoDetails
