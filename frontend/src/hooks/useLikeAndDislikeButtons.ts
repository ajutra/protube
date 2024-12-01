import { useState, useEffect } from 'react'
import { getEnv } from '@/utils/Env'

interface UseLikeAndDislikeProps {
  videoId: string
  username: string
}

interface LikeOrDislikeResponse {
  hasLiked: boolean
  hasDisliked: boolean
}

export const useLikeAndDislike = ({
  videoId,
  username,
}: UseLikeAndDislikeProps) => {
  const [isLiked, setIsLiked] = useState(false)
  const [isDisliked, setIsDisliked] = useState(false)

  const fetchUserLikeOrDislike = async () => {
    const response = await fetch(
      getEnv().API_BASE_URL + `/users/${username}/videos/${videoId}/likes`
    )

    if (!response.ok) {
      throw new Error()
    }

    const data: LikeOrDislikeResponse = await response.json()

    setIsLiked(data.hasLiked)
    setIsDisliked(data.hasDisliked)
  }

  const dislike = async () => {
    const response = await fetch(
      getEnv().API_BASE_URL + `/users/${username}/videos/${videoId}/dislike`,
      {
        method: 'POST',
      }
    )

    if (!response.ok) {
      throw new Error()
    }
  }

  const like = async () => {
    const response = await fetch(
      getEnv().API_BASE_URL + `/users/${username}/videos/${videoId}/like`,
      {
        method: 'POST',
      }
    )

    if (!response.ok) {
      throw new Error()
    }
  }

  const removeLikeOrDislike = async () => {
    const response = await fetch(
      getEnv().API_BASE_URL + `/users/${username}/videos/${videoId}/likes`,
      {
        method: 'DELETE',
      }
    )

    if (!response.ok) {
      throw new Error()
    }
  }

  useEffect(() => {
    fetchUserLikeOrDislike()
  }, [])

  return {
    isLiked,
    isDisliked,
    setIsLiked,
    setIsDisliked,
    like,
    dislike,
    removeLikeOrDislike,
  }
}
