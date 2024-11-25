import React from 'react'
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
} from '@/components/ui/dropdown-menu'
import { Button } from '@/components/ui/button'
import { EllipsisVertical, Pencil, Trash2 } from 'lucide-react'

interface CommentAndVideoActionsProps {
  onSelectEdit: () => void
  onSelectDelete: () => void
}

const CommentAndVideoActions: React.FC<CommentAndVideoActionsProps> = ({
  onSelectEdit,
  onSelectDelete,
}) => {
  return (
    <div className="flex items-center">
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="ghost" className="h-8 w-8 rounded-full">
            <EllipsisVertical />
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuItem className="cursor-pointer" onSelect={onSelectEdit}>
            <Pencil />
            Edit
          </DropdownMenuItem>
          <DropdownMenuItem
            className="cursor-pointer"
            onSelect={onSelectDelete}
          >
            <Trash2 />
            Delete
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
  )
}

export default CommentAndVideoActions
