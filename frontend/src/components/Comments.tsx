import React, { useEffect, useState } from 'react';
import Comment from './Comment';
import { useAuth } from '@/context/AuthContext';
import { LeaveComment } from './LeaveComment';
import { cn } from '@/lib/utils';
import { Comment as CommentType } from '@/model/Comment';
import { getEnv } from '@/utils/Env';

interface CommentsProps {
  comments: CommentType[];
  className?: string;
  videoId: string;
}

const Comments: React.FC<CommentsProps> = ({ comments, className, videoId }) => {
  const { username, isLoggedIn } = useAuth();

  const fetchComments = async () => {
    try {
      const response = await fetch(`${getEnv().API_BASE_URL}/videos/${videoId}/comments`);
      if (!response.ok) {
        throw new Error('Failed to fetch comments');
      }
      const data = await response.json();
      setCommentList(data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };
  useEffect(() => {
    fetchComments();
  }, [videoId]);

  const [commentList, setCommentList] = useState<CommentType[]>(comments);

  const handleDeletedComment = (commentId: string) => {
    setCommentList(
      commentList.filter((comment) => comment.commentId !== commentId)
    );
  };

  const handleNewComment = async (newComment: { videoId: string; username: string; text: string }) => {
    const commentWithId: CommentType = {
      ...newComment,
      commentId: new Date().toISOString(), 
    };
    setCommentList((prevComments) => [...prevComments, commentWithId]);
    await fetchComments();
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
