import CommentInput from '@/components/CommentInput'
import useLeaveComment from '@/hooks/useLeaveComment'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'

interface LeaveCommentProps {
  username: string
  videoId: string
  onNewComment: (newComment: {
    videoId: string
    username: string
    text: string
  }) => void
}

export function LeaveComment({
  username,
  videoId,
  onNewComment,
}: LeaveCommentProps) {
  const { handleConfirm, loading } = useLeaveComment({
    videoId,
    username,
    onNewComment,
  })

  const handleCancel = () => {
    // No need to setComment here
  }

  return (
    <div className="flex items-start space-x-4">
      <div className="flex-shrink-0">
        <Avatar>
          <AvatarFallback className="bg-primary font-bold text-background dark:text-foreground">
            {username.charAt(0).toUpperCase()}
          </AvatarFallback>
        </Avatar>
      </div>
      <CommentInput
        comment={''}
        onConfirm={handleConfirm}
        confirmButtonLabel="Submit"
        onCancel={handleCancel}
        loading={loading}
      />
    </div>
  )
}
