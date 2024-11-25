import React from 'react'
import { Comment as CommentType } from '@/model/Comment'
import { useAuth } from '@/context/AuthContext'
import { useComment } from '@/hooks/useComment'
import CommentInput from '@/components/CommentInput'
import Spinner from '@/components/Spinner'
import CommentAndVideoActions from '@/components/CommentAndVideoActions'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'

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

  return isLoading ? (
    <div className="flex justify-center">
      <Spinner />
    </div>
  ) : (
    <>
      <div className="mb-4 flex items-center">
        <div className="flex h-full w-full space-x-4">
          <div className="flex items-center">
            <Avatar>
              <AvatarFallback className="text-secondary-foreground">
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
                <p className="text-left text-sm">{commentText}</p>
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
              showButtons={true}
            />
          )}
        </div>
      </div>
    </>
  )
}

export default Comment
