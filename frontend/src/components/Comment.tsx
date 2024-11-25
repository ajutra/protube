import React, { useState } from 'react'
import { Comment as CommentType } from '@/model/Comment'
import { useAuth } from '@/context/AuthContext'
import { useComment } from '@/hooks/useComment'
import CommentInput from '@/components/CommentInput'
import Spinner from '@/components/Spinner'
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'
import CommentAndVideoActions from '@/components/CommentAndVideoActions'

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
    setShowError,
    handleOnConfirm,
    handleOnCancel,
    handleOnDelete,
  } = useComment(comment)

  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false)

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
          <div className="flex-shrink-0">
            <div className="flex h-10 w-10 items-center justify-center rounded-full bg-muted">
              {comment.username.charAt(0).toUpperCase()}
            </div>
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
                  onSelectEdit={() => setIsEditing(true)}
                  onSelectDelete={() => setShowDeleteConfirmation(true)}
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
      <AlertDialog open={showError} onOpenChange={setShowError}>
        <VisuallyHidden.Root>
          <AlertDialogTrigger></AlertDialogTrigger>
        </VisuallyHidden.Root>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Something went wrong!</AlertDialogTitle>
            <AlertDialogDescription>
              Comment could not be modified. Please try again later.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogAction>Continue</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <AlertDialog
        open={showDeleteConfirmation}
        onOpenChange={setShowDeleteConfirmation}
      >
        <VisuallyHidden.Root>
          <AlertDialogTrigger></AlertDialogTrigger>
        </VisuallyHidden.Root>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Delete Comment</AlertDialogTitle>
            <AlertDialogDescription>
              Are you sure you want to delete this comment? This action cannot
              be undone.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={handleDelete}>Delete</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  )
}

export default Comment
