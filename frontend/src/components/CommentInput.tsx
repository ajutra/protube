import React from 'react'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'

interface CommentInputProps {
  comment?: string
  confirmButtonLabel: string
  onConfirm: (newCommentText: string) => void
  onCancel: () => void
  showButtons?: boolean
}

const CommentInput: React.FC<CommentInputProps> = ({
  comment,
  confirmButtonLabel,
  onConfirm,
  onCancel,
  showButtons,
}) => {
  const [inputValue, setInputValue] = React.useState(comment || '')
  const [_showButtons, setShowButtons] = React.useState(showButtons || false)

  return (
    <div className="w-full space-y-5">
      <Input
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
        className="flex-grow rounded-none border-x-0 border-t-0 focus:border-b-2 focus:border-b-primary focus:outline-none focus-visible:ring-0"
        type="text"
        placeholder="Leave your comment..."
        onFocus={() => setShowButtons(true)}
      />
      {_showButtons && (
        <div className="flex justify-end space-x-2">
          <Button
            className="rounded-full"
            variant="ghost"
            onClick={() => {
              setInputValue('')
              setShowButtons(false)
              onCancel()
            }}
          >
            Cancel
          </Button>
          <Button
            className="rounded-full"
            disabled={comment === inputValue}
            onClick={() => onConfirm(inputValue)}
          >
            {confirmButtonLabel}
          </Button>
        </div>
      )}
    </div>
  )
}
export default CommentInput
