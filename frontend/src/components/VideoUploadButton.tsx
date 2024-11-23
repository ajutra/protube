import React, { useState } from 'react'
import { Button } from '@/components/ui/button'
import VideoUpload from '@/components/VideoUpload'
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
} from '@/components/ui/dialog'

const VideoUploadButton: React.FC = () => {
  const [open, setOpen] = useState(false)

  const handleClose = () => {
    setOpen(false)
  }

  return (
    <div>
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogTrigger asChild>
          <Button className="rounded-md px-4 py-2 font-bold">
            Upload Video
          </Button>
        </DialogTrigger>
        <DialogContent className="max-h-[90vh] max-w-4xl overflow-auto rounded-md bg-background p-6 text-foreground shadow-lg">
          <DialogTitle className="text-xl font-semibold">
            Upload Video
          </DialogTitle>
          <DialogDescription className="mb-4 text-sm">
            Use the form below to upload your video and thumbnail.
          </DialogDescription>
          <VideoUpload onUploadSuccess={handleClose} />
        </DialogContent>
      </Dialog>
    </div>
  )
}

export default VideoUploadButton
