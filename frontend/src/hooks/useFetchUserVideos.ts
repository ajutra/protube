import { useEffect, useState, useCallback } from 'react'
import { getEnv } from '@/utils/Env'
import { VideoPreviewData } from '@/model/VideoPreviewData'

const useFetchUserVideos = (username: string) => {
  const [videos, setVideos] = useState<VideoPreviewData[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<Error | null>(null)

  const fetchUserVideos = useCallback(async () => {
    setLoading(true)
    try {
      const response = await fetch(
        `${getEnv().API_BASE_URL}/users/${username}/videos`
      )
      if (!response.ok) {
        throw new Error('Failed to fetch videos')
      }
      const data = await response.json()
      setVideos(data)
    } catch (err) {
      setError(err as Error)
    } finally {
      setLoading(false)
    }
  }, [username])

  useEffect(() => {
    fetchUserVideos()
  }, [fetchUserVideos])

  return { videos, loading, error, refetch: fetchUserVideos }
}

export default useFetchUserVideos
