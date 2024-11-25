import { useState } from 'react'
import { getEnv } from '@/utils/Env'
import { useAuth } from '@/context/AuthContext'
import { useToast } from '@/hooks/use-toast'

interface Video {
  videoId: string
  title: string
  description: string
  width: number
  height: number
  duration: number
  username: string
  meta?: {
    tags?: { name: string }[]
    categories?: { name: string }[]
    comments?: any[]
  }
}

interface UseEditVideoResult {
  title: string
  description: string
  tags: string
  categories: string
  setTitle: (title: string) => void
  setDescription: (description: string) => void
  setTags: (tags: string) => void
  setCategories: (categories: string) => void
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
  const [tags, setTags] = useState<string>(
    video.meta?.tags.map((tag) => tag.name).join(', ') || ''
  )
  const [categories, setCategories] = useState<string>(
    video.meta?.categories.map((category) => category.name).join(', ') || ''
  )

  const handleSave = async () => {
    const updateVideoCommand = {
      width: video.width,
      height: video.height,
      duration: video.duration,
      title,
      description,
      username: video.username,
      tags: tags
        .split(',')
        .map((tag) => tag.trim())
        .filter((tag) => tag !== '')
        .map((tag) => ({ tagName: tag })),
      categories: categories
        .split(',')
        .map((category) => category.trim())
        .filter((category) => category !== '')
        .map((category) => ({ categoryName: category })),
      comments: video.meta?.comments || [],
    }

    try {
      const response = await fetch(`${API_BASE_URL}/videos/${video.videoId}`, {
        method: 'PUT',
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
      toast({ description: `An error occurred: ${error.message}` })
    }
  }

  return {
    title,
    description,
    tags,
    categories,
    setTitle,
    setDescription,
    setTags,
    setCategories,
    handleSave,
  }
}
