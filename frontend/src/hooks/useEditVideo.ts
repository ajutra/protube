import { useState } from 'react'
import { getEnv } from '@/utils/Env'
import { useToast } from '@/hooks/use-toast'

interface Video {
  videoId: string
  title: string
  description: string
}

interface UseEditVideoResult {
  title: string
  description: string
  setTitle: (title: string) => void
  setDescription: (description: string) => void
  handleSave: () => Promise<void>
}

export const useEditVideo = (
  video: Video,
  onSave: () => void
): UseEditVideoResult => {
  const { API_BASE_URL } = getEnv()
  const { toast } = useToast()
  const [title, setTitle] = useState<string>(video.title)
  const [description, setDescription] = useState<string>(video.description)

  const handleSave = async () => {
    const updateVideoCommand = {
      id: video.videoId,
      title,
      description,
    }

    try {
      const response = await fetch(`${API_BASE_URL}/videos`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateVideoCommand),
      })

      if (response.ok) {
        toast({ description: 'Video updated successfully!' })
        onSave()
      } else {
        const errorText = await response.text()
        toast({ description: `Failed to update video: ${errorText}` })
      }
    } catch (error) {
      const errorMessage =
        error instanceof Error ? error.message : 'Unknown error'
      toast({ description: `An error occurred: ${errorMessage}` })
    }
  }

  return {
    title,
    description,
    setTitle,
    setDescription,
    handleSave,
  }
}
