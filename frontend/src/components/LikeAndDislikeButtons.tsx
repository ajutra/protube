import React from 'react'
import { useLikeAndDislike } from '@/hooks/useLikeAndDislikeButtons'
import { Button } from '@/components/ui/button'
import { ThumbsUp, ThumbsDown, Tally1 } from 'lucide-react'
import { cn } from '@/lib/utils'
import { toast } from '@/hooks/use-toast'

interface LikeAndDislikeButtonsProps {
  videoId: string
  username: string
  videoLikes: number
  videoDislikes: number
  className?: string
}

export const LikeAndDislikeButtons: React.FC<LikeAndDislikeButtonsProps> = ({
  videoId,
  username,
  videoLikes,
  videoDislikes,
  className,
}) => {
  const [likes, setLikes] = React.useState(videoLikes)
  const [dislikes, setDislikes] = React.useState(videoDislikes)
  const {
    isLiked,
    isDisliked,
    setIsLiked,
    setIsDisliked,
    like,
    dislike,
    removeLikeOrDislike,
  } = useLikeAndDislike({ videoId, username })

  const onLike = async () => {
    if (isLiked) {
      try {
        await removeLikeOrDislike()
        setLikes(likes - 1)
        setIsLiked(false)
      } catch (error) {
        toast({
          description: 'Failed to remove like, please try again.',
          variant: 'destructive',
        })
      }
    } else {
      try {
        await like()
        if (isDisliked) {
          setDislikes(dislikes - 1)
          setIsDisliked(false)
        }
        setLikes(likes + 1)
        setIsLiked(true)
      } catch (error) {
        toast({
          description: 'Failed to like video, please try again.',
          variant: 'destructive',
        })
      }
    }
  }

  const onDislike = async () => {
    if (isDisliked) {
      try {
        await removeLikeOrDislike()
        setDislikes(dislikes - 1)
        setIsDisliked(false)
      } catch (error) {
        toast({
          description: 'Failed to remove dislike, please try again.',
          variant: 'destructive',
        })
      }
    } else {
      try {
        await dislike()
        if (isLiked) {
          setLikes(likes - 1)
          setIsLiked(false)
        }
        setDislikes(dislikes + 1)
        setIsDisliked(true)
      } catch (error) {
        toast({
          description: 'Failed to dislike video, please try again.',
          variant: 'destructive',
        })
      }
    }
  }

  return (
    <div
      className={cn([
        'inline-flex items-center rounded-full bg-secondary',
        className,
      ])}
    >
      <Button
        variant="secondary"
        onClick={onLike}
        className="inline-flex h-8 w-8 items-center justify-center gap-2 whitespace-nowrap rounded-full px-6 text-sm font-medium tracking-wide transition duration-300"
      >
        <ThumbsUp className={cn({ 'stroke-green-600': isLiked })} />
        <span>{likes}</span>
      </Button>
      <div className="h-8 w-2 items-center overflow-hidden">
        <Tally1 className="h-8" />
      </div>
      <Button
        variant="secondary"
        onClick={onDislike}
        className="full inline-flex h-8 w-8 items-center justify-center gap-2 whitespace-nowrap rounded-full px-6 text-sm font-medium tracking-wide transition duration-300"
      >
        <ThumbsDown className={cn({ 'stroke-red-600': isDisliked })} />
        <span>{dislikes}</span>
      </Button>
    </div>
  )
}
