import React from 'react'
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
import { UserAcceptance } from './UserAcceptance'

const VideoPreview: React.FC<VideoPreviewData> = ({
  videoFileName,
  thumbnailFileName,
  title,
  username,
  likes = 0,
  dislikes = 0,
}) => {
  const { isHovered, handleMouseEnter, handleMouseLeave } =
    useVideoPreviewHover()

  return (
    <>
      <Card
        className="flex h-full cursor-pointer flex-col border-none pt-5 shadow-none"
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <Card className="flex aspect-video items-center justify-center truncate">
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
              className="h-full w-full object-cover"
            />
          )}
        </Card>
        <CardContent className="mt-3 flex flex-grow flex-col space-y-2 p-0">
          <HoverCard>
            <HoverCardTrigger asChild>
              <div>
                <CardTitle className="line-clamp-2 leading-relaxed">
                  {title}
                </CardTitle>
              </div>
            </HoverCardTrigger>
            <HoverCardContent>
              <p>{title}</p>
            </HoverCardContent>
          </HoverCard>
          <CardDescription className="truncate">{username}</CardDescription>
        </CardContent>
        <UserAcceptance likes={likes} dislikes={dislikes} />
      </Card>
    </>
  )
}

export default VideoPreview
