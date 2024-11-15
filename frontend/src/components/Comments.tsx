import React from 'react';
import { Comment } from  '../model/Comment';

const Comments: React.FC<{ comments: Comment[] }> = ({ comments }) => {
  return (
    <div className="comments mt-4">
      <h4 className="text-secondary border-bottom pb-2 text-start">{comments.length} COMMENTS:</h4>
      {comments.length > 0 ? (
        comments.map((comment) => (
          <div key={comment.videoId} className="comment mb-2 p-3 border rounded bg-secondary w-100">
            <strong className="fs-5 text-start d-block">{comment.username}</strong>
            <p className="mb-0 fs-6 text-start">{comment.text}</p>
          </div>
        ))
      ) : (
        <p className="text-muted fs-5">No comments available</p>
      )}
    </div>
  );
};

export default Comments;