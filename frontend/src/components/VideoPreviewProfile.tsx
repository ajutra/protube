import React, { useState } from 'react'
import { VideoPreviewData } from '@/model/VideoPreviewData'
import { getEnv } from '@/utils/Env'
import useVideoPreviewHover from '@/hooks/useVideoPreviewHover'
import {
  Card,
  CardContent,
  CardDescription,
  CardTitle,
} from '@/components/ui/card'
import {
  HoverCard,
  HoverCardContent,
  HoverCardTrigger,
} from '@/components/ui/hover-card'
import CommentAndVideoActions from '@/components/CommentAndVideoActions'
import useDeleteVideo from '@/hooks/useDeleteVideo'
import Spinner from './Spinner'
import EditVideoForm from './EditVideoForm'
import { AppRoutes } from '@/enums/AppRoutes'
import { Link } from 'react-router-dom'
import { Dialog, DialogContent, DialogOverlay } from '@/components/ui/dialog'

interface VideoPreviewProfileProps extends VideoPreviewData {
  onDelete: () => void
}

const VideoPreviewProfile: React.FC<VideoPreviewProfileProps> = ({
  videoId,
  videoFileName,
  thumbnailFileName,
  title,
  meta,
  onDelete,
}) => {
  const { isHovered, handleMouseEnter, handleMouseLeave } =
    useVideoPreviewHover()
  const { isLoading, showErrorDeletingVideo, handleOnDeleteVideo } =
    useDeleteVideo(videoId, false)

  const [isEditing, setIsEditing] = useState(false)

  const handleEditVideo = () => {
    setIsEditing(true)
  }

  const handleSaveEdit = () => {
    setIsEditing(false)
  }

  const handleCancelEdit = () => {
    setIsEditing(false)
  }

  return isLoading ? (
    <div className="flex h-screen items-center justify-center">
      <Spinner />
    </div>
  ) : (
    <>
      <Card className="flex cursor-pointer flex-row gap-5 border-none shadow-none">
        <Card
          className="flex aspect-video w-1/3 items-center justify-center truncate border-none shadow-none"
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        >
          <Link to={AppRoutes.VIDEO_DETAILS + '?id=' + videoId}>
            {isHovered ? (
              <video
                src={`${getEnv().MEDIA_BASE_URL}/${videoFileName}`}
                autoPlay
                loop
                muted
                role="video"
                className="object-cover"
              />
            ) : (
              <img
                src={`${getEnv().MEDIA_BASE_URL}/${thumbnailFileName}`}
                alt={thumbnailFileName}
                className="object-cover"
              />
            )}
          </Link>
        </Card>
        <CardContent className="mt-3 flex w-full flex-col space-y-2 p-0">
          <div className="flex items-start justify-between">
            <HoverCard>
              <HoverCardTrigger asChild>
                <CardTitle className="line-clamp-2 leading-relaxed">
                  <Link to={AppRoutes.VIDEO_DETAILS + '?id=' + videoId}>
                    {title}
                  </Link>
                </CardTitle>
              </HoverCardTrigger>
              <HoverCardContent>
                <p>{title}</p>
              </HoverCardContent>
            </HoverCard>
            <CommentAndVideoActions
              buttonVariant="secondary"
              openEditDialog={showErrorDeletingVideo}
              editDialogTitle="Something went wrong!"
              editDialogDescription="Video could not be deleted. Please try again later."
              deleteDialogTitle="Delete Video"
              deleteDialogDescription="Are you sure you want to delete this video? This action cannot be undone."
              onSelectEdit={handleEditVideo}
              onSelectDelete={() => handleOnDeleteVideo(onDelete)}
            />
          </div>
          <CardDescription className="flex cursor-default flex-row">
            <div className="line-clamp-2">{meta?.description}</div>
          </CardDescription>
        </CardContent>
      </Card>
      <Dialog open={isEditing} onOpenChange={setIsEditing}>
        <DialogOverlay />
        <DialogContent className="max-w-2xl p-6">
          <EditVideoForm
            video={{ videoId, title, description: meta?.description }}
            onSave={handleSaveEdit}
            onCancel={handleCancelEdit}
          />
        </DialogContent>
      </Dialog>
    </>
  )
}

export default VideoPreviewProfile
