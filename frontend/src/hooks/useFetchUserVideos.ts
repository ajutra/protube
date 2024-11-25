import { useEffect, useState } from 'react';
import { getEnv } from '@/utils/Env';
import { VideoPreviewData } from '@/model/VideoPreviewData';

const useFetchUserVideos = (username: string) => {
  const [videos, setVideos] = useState<VideoPreviewData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchUserVideos = async () => {
      try {
        const response = await fetch(`${getEnv().API_BASE_URL}/users/${username}/videos`);
        if (!response.ok) {
          throw new Error('Failed to fetch videos');
        }
        const data = await response.json();
        setVideos(data);
      } catch (err) {
        setError(err as Error);
      } finally {
        setLoading(false);
      }
    };

    fetchUserVideos();
  }, [username]);

  return { videos, loading, error };
};

export default useFetchUserVideos;