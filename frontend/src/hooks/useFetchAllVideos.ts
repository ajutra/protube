import { useState, useEffect } from 'react'
import { VideoPreviewData } from '@/model/VideoPreviewData'

const useFetchAllVideos = (url: string) => {
  const [videos, setVideos] = useState<VideoPreviewData[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<Error | null>(null)

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(url)
        if (!response.ok) {
          throw new Error('Network response was not ok')
        }
        const result = await response.json()
        setVideos(result)
      } catch (error) {
        setError(error as Error)
      } finally {
        setLoading(false)
      }
    }

    fetchData()
  }, [url])

  return { videos, loading, error }
}

export default useFetchAllVideos
