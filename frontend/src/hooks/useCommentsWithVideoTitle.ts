import { useState, useEffect } from 'react'
import useFetchAllUserComments from '@/hooks/useFetchAllUserComments'
import useGetVideoDetailsForComments from '@/hooks/useGetVideoDetailsForComments'

const useCommentsWithVideoTitle = (username: string) => {
  const comments = useFetchAllUserComments(username || '')
  const { videoDetails, groupedComments: initialGroupedComments } =
    useGetVideoDetailsForComments(comments)
  const [groupedComments, setGroupedComments] = useState(initialGroupedComments)

  useEffect(() => {
    setGroupedComments(initialGroupedComments)
  }, [initialGroupedComments])

  const handleDeletedComment = (videoId: string, commentId: string) => {
    setGroupedComments((prevGroupedComments) => {
      const updatedComments = prevGroupedComments[videoId].filter(
        (comment) => comment.commentId !== commentId
      )
      if (updatedComments.length === 0) {
        const { [videoId]: _, ...rest } = prevGroupedComments
        return rest
      }
      return {
        ...prevGroupedComments,
        [videoId]: updatedComments,
      }
    })
  }

  const hasComments = Object.keys(groupedComments).length > 0

  return {
    videoDetails,
    groupedComments,
    handleDeletedComment,
    hasComments,
  }
}

export default useCommentsWithVideoTitle
