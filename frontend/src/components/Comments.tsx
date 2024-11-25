import React, { useState } from 'react'
import { Comment as CommentType } from '../model/Comment'
import Comment from './Comment'
import { useAuth } from '@/context/AuthContext'
import { LeaveComment } from './LeaveComment'

const Comments: React.FC<{ comments: CommentType[], videoId: string }> = ({ comments, videoId }) => {
  const [commentList, setCommentList] = useState(comments)
  const { username, isLoggedIn } = useAuth();

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
      {isLoggedIn && <LeaveComment username={username || ''} videoId={videoId || ''} />}
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
