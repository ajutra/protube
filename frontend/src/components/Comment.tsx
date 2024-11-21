import React, { useState } from 'react'
import { Comment as CommentType } from '@/model/Comment'
import { EllipsisVertical, Pencil, Trash2 } from 'lucide-react'
import { useAuth } from '@/context/AuthContext'
import { useComment } from '@/hooks/useComment'
import CommentInput from '@/components/CommentInput'
import Spinner from '@/components/Spinner'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
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
import { Button } from '@/components/ui/button'

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
      <div className="mb-4">
        <div className="flex items-start space-x-4">
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
                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <Button variant="ghost" className="h-8 w-8 rounded-full">
                      <EllipsisVertical />
                    </Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="end">
                    <DropdownMenuItem
                      className="cursor-pointer"
                      onSelect={() => setIsEditing(true)}
                    >
                      <Pencil />
                      Edit
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      className="cursor-pointer"
                      onSelect={() => setShowDeleteConfirmation(true)}
                    >
                      <Trash2 />
                      Delete
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
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
