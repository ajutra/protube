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
import { Avatar, AvatarFallback } from '@/components/ui/avatar'

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
        className="flex h-full cursor-pointer flex-col border-none shadow-none"
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
          <div className="flex items-start space-x-2">
            <Avatar className="mt-1.5 h-10 w-10">
              <AvatarFallback className="bg-primary font-bold text-background dark:text-foreground">
                {username.charAt(0).toUpperCase()}
              </AvatarFallback>
            </Avatar>
            <HoverCard>
              <HoverCardTrigger asChild>
                <div className="flex flex-col">
                  <CardTitle className="line-clamp-2 leading-relaxed">
                    {title}
                  </CardTitle>
                  <CardDescription className="line-clamp-1">
                    {username}
                  </CardDescription>
                </div>
              </HoverCardTrigger>
              <HoverCardContent>
                <p>{title}</p>
              </HoverCardContent>
            </HoverCard>
          </div>
        </CardContent>
        <UserAcceptance likes={likes} dislikes={dislikes} />
      </Card>
    </>
  )
}

export default VideoPreview
