import React from 'react'
import { Comment as CommentType } from '../model/Comment'
import Comment from './Comment'

const Comments: React.FC<{ comments: CommentType[] }> = ({ comments }) => {
  return (
    <div className="mt-4 space-y-8">
      <h2 className="text-left text-2xl font-bold">
        {comments.length} Comments
      </h2>
      {comments.length > 0 ? (
        comments.map((comment) => (
          <Comment key={comment.commentId} comment={comment} />
        ))
      ) : (
        <p className="text-sm">No comments available</p>
      )}
    </div>
  )
}

export default Comments
