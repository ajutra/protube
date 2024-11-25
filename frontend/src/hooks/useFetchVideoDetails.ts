import { useState, useEffect } from 'react'
import { VideoPreviewData } from '@/model/VideoPreviewData'
import { getEnv } from '@/utils/Env'

const useFetchVideoDetails = (videoId: string | null) => {
  const [video, setVideo] = useState<VideoPreviewData | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<Error | null>(null)

  useEffect(() => {
    const fetchData = async () => {
      if (!videoId) {
        setError(new Error('Invalid video ID'))
        setLoading(false)
        return
      }

      try {
        const response = await fetch(
          `${getEnv().API_BASE_URL}/videos/${videoId}`
        )
        if (!response.ok) {
          throw new Error('Network response was not ok')
        }
        const result = await response.json()
        setVideo(result)
        console.log(result)
      } catch (error) {
        setError(error as Error)
      } finally {
        setLoading(false)
      }
    }

    fetchData()
  }, [videoId])

  return { video, loading, error }
}

export default useFetchVideoDetails
