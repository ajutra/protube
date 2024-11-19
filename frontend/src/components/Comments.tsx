import React from 'react'
import { Comment as CommentType } from '../model/Comment'
import Comment from './Comment'
import { Separator } from './ui/separator'

const Comments: React.FC<{ comments: CommentType[] }> = ({ comments }) => {
  return (
    <div className="mt-4 p-4">
      <h2 className="text-left text-2xl font-bold">
        {comments.length} Comments
      </h2>
      <Separator className="my-4" />
      {comments.length > 0 ? (
        comments.map((comment, index) => (
          <Comment
            key={index}
            comment={comment}
            isLast={index === comments.length - 1}
          />
        ))
      ) : (
        <p className="text-sm">No comments available</p>
      )}
    </div>
  )
}

export default Comments
