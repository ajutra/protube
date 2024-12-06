import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getEnv } from '@/utils/Env'
import { AppRoutes } from '@/enums/AppRoutes'
import { useToast } from '@/hooks/use-toast'

const useDeleteVideo = (videoId: string | undefined, redirect: boolean) => {
  const [isLoading, setIsLoading] = useState(false)
  const [showErrorDeletingVideo, setShowErrorDeletingVideo] = useState(false)
  const { toast } = useToast()
  const navigate = useNavigate()

  const handleOnDeleteVideo = async (onDeleteSuccess?: () => void) => {
    if (!videoId) return

    setIsLoading(true)
    await fetch(getEnv().API_ALL_VIDEOS_URL + `/${videoId}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          toast({ description: 'Video deleted successfully' })
          if (redirect) navigate(AppRoutes.HOME)
          if (onDeleteSuccess) onDeleteSuccess()
        } else {
          setShowErrorDeletingVideo(true)
        }
      })
      .catch(() => {
        setShowErrorDeletingVideo(true)
      })
      .finally(() => {
        setIsLoading(false)
      })
  }

  return {
    isLoading,
    showErrorDeletingVideo,
    handleOnDeleteVideo,
  }
}

export default useDeleteVideo
