import { useState } from 'react'
import { getEnv } from '@/utils/Env'
import { Comment as CommentType } from '@/model/Comment'
import { toast } from '@/hooks/use-toast'

export const useComment = (comment: CommentType) => {
  const [isEditing, setIsEditing] = useState(false)
  const [commentText, setCommentText] = useState(comment.text)
  const [isLoading, setIsLoading] = useState(false)
  const [showError, setShowError] = useState(false)

  const handleOnConfirm = async (newCommentText: string) => {
    setIsEditing(false)
    setIsLoading(true)

    try {
      const response = await fetch(getEnv().API_BASE_URL + `/comments`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          commentId: comment.commentId,
          text: newCommentText,
        }),
      })

      if (!response.ok) {
        throw new Error('Failed to update comment')
      }

      setCommentText(newCommentText)
    } catch (error) {
      setShowError(true)
    } finally {
      setIsLoading(false)
    }
  }

  const handleOnCancel = () => {
    setIsEditing(false)
  }

  const handleOnDelete = async () => {
    setIsLoading(true)
    try {
      const response = await fetch(
        getEnv().API_BASE_URL + `/comments/${comment.commentId}`,
        {
          method: 'DELETE',
        }
      )

      if (!response.ok) {
        throw new Error('Failed to delete comment')
      }

      toast({ description: 'Comment deleted successfully' })
      return true
    } catch (error) {
      setShowError(true)
      return false
    } finally {
      setIsLoading(false)
    }
  }

  return {
    isEditing,
    commentText,
    isLoading,
    showError,
    setIsEditing,
    setShowError,
    handleOnConfirm,
    handleOnCancel,
    handleOnDelete,
  }
}
