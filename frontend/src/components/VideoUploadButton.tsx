import React, { useState } from 'react'
import { Button } from '@/components/ui/button'
import VideoUpload from '@/components/VideoUpload'
import {
  Dialog,
  DialogTitle,
  DialogTrigger,
  DialogContent,
  DialogDescription,
} from '@/components/ui/dialog'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'

interface VideoUploadButtonProps {
  onUploadSuccess: () => void
}

const VideoUploadButton: React.FC<VideoUploadButtonProps> = ({
  onUploadSuccess,
}) => {
  const [open, setOpen] = useState(false)

  const handleClose = () => {
    setOpen(false)
    onUploadSuccess()
  }

  return (
    <div>
      <Dialog open={open} onOpenChange={setOpen}>
        <VisuallyHidden.Root>
          <DialogTitle>Upload a new Video</DialogTitle>
        </VisuallyHidden.Root>
        <DialogTrigger asChild>
          <Button className="rounded-md px-4 py-2 font-bold dark:text-foreground">
            Upload a new video
          </Button>
        </DialogTrigger>
        <DialogContent className="max-h-[90vh] max-w-4xl overflow-auto rounded-md bg-background p-6 text-foreground shadow-lg">
          <VisuallyHidden.Root>
            <DialogDescription>Upload a new video</DialogDescription>
          </VisuallyHidden.Root>
          <VideoUpload onUploadSuccess={handleClose} />
        </DialogContent>
      </Dialog>
    </div>
  )
}

export default VideoUploadButton
