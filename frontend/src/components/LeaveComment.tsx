import { useState } from 'react';
import { Input } from '@/components/ui/input';
import { getEnv } from '@/utils/Env';

interface LeaveCommentProps {
  username: string;
  videoId: string;
}

export function LeaveComment({ username, videoId }: LeaveCommentProps) {
  const [comment, setComment] = useState('');

  const handleKeyDown = async (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      // Realizar la solicitud POST para enviar el comentario
      try {
        const response = await fetch(getEnv().API_BASE_URL + '/comments', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ videoId, username, text: comment }),
        });

        if (response.ok) {
          console.log('Comentario enviado');
          // Borrar el texto del input
          setComment('');
        } else {
          console.error('Error al enviar el comentario');
        }
      } catch (error) {
        console.error('Error al enviar el comentario', error);
      }
    }
  };

  return (
    <div className="flex items-center space-x-4">
      <div className="flex-shrink-0">
        <div className="flex h-10 w-10 items-center justify-center rounded-full bg-muted">
          {username.charAt(0).toUpperCase()}
        </div>
      </div>
      <div className="flex w-full items-center space-x-2">
        <Input
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          onKeyDown={handleKeyDown}
          className="flex-grow rounded-none border-x-0 border-t-0 focus:border-b-2 focus:border-b-primary focus:outline-none focus-visible:ring-0"
          type="text"
          placeholder="Leave your comment..."
        />
      </div>
    </div>
  );
}