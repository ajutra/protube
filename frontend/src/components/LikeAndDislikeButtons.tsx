import { ThumbsUp } from 'lucide-react'
import { ThumbsDown } from 'lucide-react'
import { Tally1 } from 'lucide-react'
import { Button } from '@/components/ui/button'
import { useState } from 'react'
import { cn } from '@/lib/utils'

interface LikeAndDislikeButtonsProps {
  videoId: string
  username: string | null
  className?: string
}

export const LikeAndDislikeButtons: React.FC<LikeAndDislikeButtonsProps> = ({
  videoId,
  username,
  className,
}) => {
  const [likes, setLikes] = useState(0)
  const [dislikes, setDislikes] = useState(0)
  const [isLiked, setIsLiked] = useState(false)
  const [isDisliked, setIsDisliked] = useState(false)
  const onLike = () => {
    if (isLiked) {
      setLikes(likes - 1)
      setIsLiked(false)
    } else {
      if (isDisliked) {
        setDislikes(dislikes - 1)
        setIsDisliked(false)
      }
      setLikes(likes + 1)
      setIsLiked(true)
    }
  }
  const onDislike = () => {
    if (isDisliked) {
      setDislikes(dislikes - 1)
      setIsDisliked(false)
    } else {
      if (isLiked) {
        setLikes(likes - 1)
        setIsLiked(false)
      }
      setDislikes(dislikes + 1)
      setIsDisliked(true)
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
        className="inline-flex h-8 w-8 items-center justify-center gap-2 whitespace-nowrap rounded-full px-6 text-sm font-medium tracking-wide transition duration-300 focus-visible:outline-none disabled:shadow-none"
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
        className="full inline-flex h-8 w-8 items-center justify-center gap-2 whitespace-nowrap rounded-full px-6 text-sm font-medium tracking-wide transition duration-300 focus-visible:outline-none disabled:shadow-none"
      >
        <ThumbsDown className={cn({ 'stroke-red-600': isDisliked })} />
        <span>{dislikes}</span>
      </Button>
    </div>
  )
}
