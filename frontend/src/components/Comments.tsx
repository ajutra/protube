import React, { useState } from 'react';
import Comment from './Comment';
import { useAuth } from '@/context/AuthContext';
import { LeaveComment } from './LeaveComment';
import { cn } from '@/lib/utils';
import { Comment as CommentType } from '@/model/Comment';

interface CommentsProps {
  comments: CommentType[];
  className?: string;
  videoId: string;
}

const Comments: React.FC<CommentsProps> = ({ comments, className, videoId }) => {
  const { username, isLoggedIn } = useAuth();

  
  const sortedComments = [...comments].sort((a, b) => {
    if (a.username === username) return -1;
    if (b.username === username) return 1;
    return 0;
  });

  const [commentList, setCommentList] = useState(sortedComments);

  const handleDeletedComment = (commentId: string) => {
    setCommentList(
      commentList.filter((comment) => comment.commentId !== commentId)
    );
  };

  const handleNewComment = (newComment: { videoId: string; username: string; text: string }) => {
    const commentWithId: CommentType = {
      ...newComment,
      commentId: new Date().toISOString(), 
    };
    setCommentList((prevComments) => {
      const updatedComments = [...prevComments, commentWithId];
      return updatedComments.sort((a, b) => {
        if (a.username === username) return -1;
        if (b.username === username) return 1;
        return 0;
      });
    });
  };

  return (
    <div className={cn(['space-y-8', className])}>
      <h2 className="text-left text-2xl font-bold">
        {commentList.length} Comments
      </h2>
      {isLoggedIn && (
        <LeaveComment
          username={username || ''}
          videoId={videoId || ''}
          onNewComment={handleNewComment}
        />
      )}
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
  );
};

export default Comments;
