import React from 'react'
import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import CommentInput from '@/components/CommentInput'
import Spinner from '@/components/Spinner'
import { Comment as CommentType } from '@/model/Comment'
import { EllipsisVertical, Pencil } from 'lucide-react'
import { getEnv } from '@/utils/Env'
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'
import { useAuth } from '@/context/AuthContext'

const Comment: React.FC<{ comment: CommentType }> = ({ comment }) => {
  const [isEditing, setIsEditing] = React.useState(false)
  const [commentText, setCommentText] = React.useState(comment.text)
  const [isLoading, setIsLoading] = React.useState(false)
  const [showError, setShowError] = React.useState(false)
  const { username } = useAuth()

  const handleOnConfirm = async (newCommentText: string) => {
    setIsEditing(false)
    setIsLoading(true)

    try {
      const response = await fetch(getEnv().API_BASE_URL + `/comments`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          commentId: comment.commentId,
          text: newCommentText,
        }),
      })

      if (!response.ok) {
        throw new Error('Failed to update comment')
      }

      setCommentText(newCommentText)
    } catch (error) {
      console.error('Error updating comment:', error)
      setShowError(true)
    } finally {
      setIsLoading(false)
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
                  </DropdownMenuContent>
                </DropdownMenu>
              )}
            </>
          ) : (
            <CommentInput
              comment={commentText}
              confirmButtonLabel={'Save'}
              onConfirm={handleOnConfirm}
              onCancel={() => setIsEditing(false)}
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
              Comment could not be edited. Please try again later.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogAction>Continue</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  )
}

export default Comment
