import React from 'react'
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
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
import { Button } from '@/components/ui/button'
import { EllipsisVertical, Pencil, Trash2 } from 'lucide-react'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'

interface CommentAndVideoActionsProps {
  buttonVariant?:
    | 'default'
    | 'secondary'
    | 'destructive'
    | 'outline'
    | 'ghost'
    | 'link'
  openEditDialog: boolean
  editDialogTitle: string
  editDialogDescription: string
  deleteDialogTitle: string
  deleteDialogDescription: string
  onSelectEdit: () => void
  onSelectDelete: () => void
}

const CommentAndVideoActions: React.FC<CommentAndVideoActionsProps> = ({
  buttonVariant = 'ghost',
  openEditDialog,
  editDialogTitle,
  editDialogDescription,
  deleteDialogTitle,
  deleteDialogDescription,
  onSelectEdit,
  onSelectDelete,
}) => {
  const [isEditDialogOpen, setIsEditDialogOpen] = React.useState(openEditDialog)
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = React.useState(false)

  return (
    <>
      <div className="flex items-center">
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant={buttonVariant} className="h-8 w-8 rounded-full">
              <EllipsisVertical />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuItem
              className="cursor-pointer"
              onSelect={onSelectEdit}
            >
              <Pencil />
              Edit
            </DropdownMenuItem>
            <DropdownMenuItem
              className="cursor-pointer"
              onSelect={() => setIsDeleteDialogOpen(true)}
            >
              <Trash2 />
              Delete
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
      <AlertDialog open={isEditDialogOpen} onOpenChange={setIsEditDialogOpen}>
        <VisuallyHidden.Root>
          <AlertDialogTrigger></AlertDialogTrigger>
        </VisuallyHidden.Root>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>{editDialogTitle}</AlertDialogTitle>
            <AlertDialogDescription>
              {editDialogDescription}
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogAction>Continue</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <AlertDialog
        open={isDeleteDialogOpen}
        onOpenChange={setIsDeleteDialogOpen}
      >
        <VisuallyHidden.Root>
          <AlertDialogTrigger></AlertDialogTrigger>
        </VisuallyHidden.Root>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>{deleteDialogTitle}</AlertDialogTitle>
            <AlertDialogDescription>
              {deleteDialogDescription}
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={onSelectDelete}>
              Delete
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  )
}

export default CommentAndVideoActions
