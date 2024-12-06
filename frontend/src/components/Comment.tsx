import React from 'react'
import { Comment as CommentType } from '@/model/Comment'
import { useAuth } from '@/context/AuthContext'
import { useComment } from '@/hooks/useComment'
import CommentInput from '@/components/CommentInput'
import Spinner from '@/components/Spinner'
import CommentAndVideoActions from '@/components/CommentAndVideoActions'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import processDescription from '@/utils/processDescription'

const Comment: React.FC<{ comment: CommentType; onDelete: () => void }> = ({
  comment,
  onDelete,
}) => {
  const { username } = useAuth()
  const {
    isEditing,
    commentText,
    isLoading,
    showError,
    setIsEditing,
    handleOnConfirm,
    handleOnCancel,
    handleOnDelete,
  } = useComment(comment)

  const handleDelete = async () => {
    const success = await handleOnDelete()
    if (success) {
      onDelete()
    }
  }

  const { processedDescription: processedCommentText } =
    processDescription(commentText)

  return isLoading ? (
    <div className="flex justify-center">
      <Spinner />
    </div>
  ) : (
    <>
      <div className="mb-4 flex items-center">
        <div className="flex h-full w-full space-x-4">
          <div className="flex items-start">
            <Avatar className="mt-1">
              <AvatarFallback className="bg-primary font-bold text-background dark:text-foreground">
                {comment.username.charAt(0).toUpperCase()}
              </AvatarFallback>
            </Avatar>
          </div>
          {!isEditing ? (
            <>
              <div className="flex-1">
                <div className="mb-2 text-left text-base font-semibold">
                  {comment.username}
                </div>
                <div className="text-left text-sm">{processedCommentText}</div>
              </div>
              {username === comment.username && (
                <CommentAndVideoActions
                  openEditDialog={showError}
                  editDialogTitle="Something went wrong!"
                  editDialogDescription="Comment could not be modified. Please try again later."
                  deleteDialogTitle="Delete Comment"
                  deleteDialogDescription="Are you sure you want to delete this comment? This action cannot be undone."
                  onSelectEdit={() => setIsEditing(true)}
                  onSelectDelete={() => handleDelete()}
                />
              )}
            </>
          ) : (
            <CommentInput
              comment={commentText}
              confirmButtonLabel={'Save'}
              onConfirm={handleOnConfirm}
              onCancel={handleOnCancel}
              loading={true}
            />
          )}
        </div>
      </div>
    </>
  )
}

export default Comment
