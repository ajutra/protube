import React from 'react'
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

const useQuery = () => new URLSearchParams(useLocation().search)

const VideoDetails: React.FC = () => {
  const query = useQuery()
  const videoId = query.get('id')
  const { video, loading, error } = useFetchVideoDetails(videoId)
  const [openDescription, setOpenDescription] = React.useState(false)
  const { processedDescription, lineCount } = processDescription(
    video?.meta?.description || ''
  )

  const handleError = (
    event: React.SyntheticEvent<HTMLVideoElement, Event>
  ) => {
    console.error('Video Error:', event)
  }

  if (loading)
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
          <CardTitle className="w-full text-left text-xl font-extrabold">
            {video.title}
          </CardTitle>
          <CardDescription className="flex w-full items-center space-x-2 text-secondary-foreground">
            <Avatar>
              <AvatarFallback>
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
          <Comments className="w-full" comments={video.meta?.comments || []} />
        </CardContent>
      </Card>
    </div>
  )
}

export default VideoDetails
