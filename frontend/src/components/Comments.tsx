import React, { useState } from 'react'
import { Comment as CommentType } from '../model/Comment'
import Comment from './Comment'

const Comments: React.FC<{ comments: CommentType[] }> = ({ comments }) => {
  const [commentList, setCommentList] = useState(comments)

  const handleDeletedComment = (commentId: string) => {
    setCommentList(
      commentList.filter((comment) => comment.commentId !== commentId)
    )
  }

  return (
    <div className="mt-4 space-y-8">
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
