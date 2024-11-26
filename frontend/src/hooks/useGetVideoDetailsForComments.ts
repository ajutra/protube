// src/hooks/useVideoDetails.ts
import { useEffect, useState } from 'react';
import { Comment as CommentType } from '@/model/Comment';
import { getEnv } from '@/utils/Env';

const useGetVideoDetailsForComments = (comments: CommentType[]) => {
  const [videoDetails, setVideoDetails] = useState<{ [key: string]: any }>({});
  const [groupedComments, setGroupedComments] = useState<{ [key: string]: CommentType[] }>({});

  useEffect(() => {
    const fetchVideoDetails = async () => {
      const details: { [key: string]: any } = {};
      for (const comment of comments) {
        if (!details[comment.videoId]) {
          const response = await fetch(getEnv().API_BASE_URL + `/videos/${comment.videoId}`);
          const videoDetail = await response.json();
          details[comment.videoId] = videoDetail;
        }
      }
      setVideoDetails(details);
    };

    fetchVideoDetails();
  }, [comments]);

  useEffect(() => {
    const grouped = comments.reduce((acc, comment) => {
      if (!acc[comment.videoId]) {
        acc[comment.videoId] = [];
      }
      acc[comment.videoId].push(comment);
      return acc;
    }, {} as { [key: string]: CommentType[] });

    setGroupedComments(grouped);
  }, [comments]);

  return { videoDetails, groupedComments };
};

export default useGetVideoDetailsForComments;