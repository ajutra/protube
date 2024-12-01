// src/hooks/useLeaveComment.ts
import { useState } from 'react';
import { getEnv } from '@/utils/Env';

interface UseLeaveCommentProps {
  videoId: string;
  username: string;
  onNewComment: (newComment: { videoId: string; username: string; text: string }) => void;
}

const useLeaveComment = ({ videoId, username, onNewComment }: UseLeaveCommentProps) => {
  const [loading, setLoading] = useState(false);

  const handleConfirm = async (commentText: string) => {
    setLoading(true);
    try {
      const response = await fetch(getEnv().API_BASE_URL + '/comments', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ videoId, username, text: commentText }),
      });

      if (response.ok) {
        console.log('Comentario enviado');
        onNewComment({ videoId, username, text: commentText });
      } else {
        console.error('Error sending comment', response);
      }
    } catch (error) {
      console.error('Error sending comment', error);
    } finally {
      setLoading(false);
    }
  };

  return { handleConfirm, loading };
};

export default useLeaveComment;