import React from 'react';
import { Comment as CommentType } from '../model/Comment';
import Comment from './Comment';
import { Separator } from './ui/separator';
import { LeaveComment } from './LeaveComment';
import { useAuth } from '@/context/AuthContext';
import useCommentsWithVideoTitle from '@/hooks/useCommentsWithVideoTitle';

interface CommentsOnVideoDetailsProps {
  comments: CommentType[];
  videoId: string;
}

const CommentsOnVideoDetails: React.FC<CommentsOnVideoDetailsProps> = ({ comments, videoId }) => {
  const username = useAuth().username;
  const {handleDeletedComment} = useCommentsWithVideoTitle(username || '');

  return (
    <div className="mt-4 p-4">
      <h2 className="text-left text-2xl font-bold mb-6">
        {comments.length} Comments
      </h2>
      <LeaveComment username={username || ''} videoId={videoId || ''} />
      {comments.length > 0 ? (
        comments.map((comment, index) => (
          <React.Fragment key={index}>
            <Separator className="my-4" />
            <Comment comment={comment} onDelete={() => handleDeletedComment(videoId, comment.commentId)} />
          </React.Fragment>
        ))
      ) : (
        <p className="text-sm">No comments available</p>
      )}
    </div>
  );
};

export default CommentsOnVideoDetails;
