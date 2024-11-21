import { useState } from 'react'
import { getEnv } from '@/utils/Env'
import CommentInput from '@/components/CommentInput'

interface LeaveCommentProps {
  username: string
  videoId: string
}

export function LeaveComment({ username, videoId }: LeaveCommentProps) {
  const [comment, setComment] = useState('')

  const handleConfirm = async (commentText: string) => {
    try {
      const response = await fetch(getEnv().API_BASE_URL + '/comments', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ videoId, username, text: commentText }),
      })

      if (response.ok) {
        console.log('Comentario enviado')
        setComment(commentText)
      } else {
        console.error('Error al enviar el comentario')
      }
    } catch (error) {
      console.error('Error al enviar el comentario', error)
    }
  }

  const handleCancel = () => {
    setComment('')
  }

  return (
    <div className="flex items-start space-x-4">
      <div className="flex-shrink-0">
        <div className="flex h-10 w-10 items-center justify-center rounded-full bg-muted">
          {username.charAt(0).toUpperCase()}
        </div>
      </div>
      <CommentInput
        comment={comment}
        onConfirm={handleConfirm}
        confirmButtonLabel="Submit"
        onCancel={handleCancel}
        showButtons={false}
      />
    </div>
  )
}
