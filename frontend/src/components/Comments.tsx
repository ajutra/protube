import React, { useState } from 'react'
import { Comment as CommentType } from '../model/Comment'
import Comment from './Comment'
import { cn } from '@/lib/utils'

interface CommentsProps {
  comments: CommentType[]
  className?: string
}

const Comments: React.FC<CommentsProps> = ({ comments, className }) => {
  const [commentList, setCommentList] = useState(comments)

  const handleDeletedComment = (commentId: string) => {
    setCommentList(
      commentList.filter((comment) => comment.commentId !== commentId)
    )
  }

  return (
    <div className={cn(['space-y-8', className])}>
      <h2 className="text-left text-2xl font-bold">
        {commentList.length} Comments
      </h2>
      {commentList.length > 0 ? (
        commentList.map((comment) => (
          <Comment
            key={comment.commentId}
            comment={comment}
            onDelete={() => handleDeletedComment(comment.commentId)}
          />
        ))
      ) : (
        <p className="text-sm">No comments available</p>
      )}
    </div>
  )
}

export default Comments
