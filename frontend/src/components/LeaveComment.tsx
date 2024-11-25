import CommentInput from '@/components/CommentInput';
import useLeaveComment from '@/hooks/useLeaveComment';

interface LeaveCommentProps {
  username: string;
  videoId: string;
  onNewComment: (newComment: { videoId: string; username: string; text: string }) => void;
}

export function LeaveComment({ username, videoId, onNewComment }: LeaveCommentProps) {
  const { handleConfirm, loading } = useLeaveComment({ videoId, username, onNewComment });

  const handleCancel = () => {
    // No need to setComment here
  };

  return (
    <div className="flex items-start space-x-4">
      <div className="flex-shrink-0">
        <div className="flex h-10 w-10 items-center justify-center rounded-full bg-muted">
          {username.charAt(0).toUpperCase()}
        </div>
      </div>
      <CommentInput
        onConfirm={handleConfirm}
        confirmButtonLabel="Submit"
        onCancel={handleCancel}
        loading={loading}
      />
    </div>
  );
}
