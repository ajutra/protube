import React from 'react'
import { Comment as CommentType } from '../model/Comment'
import { Separator } from './ui/separator'

const Comment: React.FC<{ comment: CommentType; isLast: boolean }> = ({
  comment,
  isLast,
}) => {
  return (
    <div className="mb-4">
      <div className="flex items-start space-x-4">
        <div className="flex-shrink-0">
          <div className="flex h-10 w-10 items-center justify-center rounded-full bg-muted">
            {comment.username.charAt(0).toUpperCase()}
          </div>
        </div>
        <div className="flex-1">
          <div className="mb-2 text-left text-base font-semibold">
            {comment.username}
          </div>
          <p className="text-left text-sm">{comment.text}</p>
        </div>
      </div>
      {!isLast && <Separator className="my-4" />}
    </div>
  )
}

export default Comment
