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
          <Button className="rounded px-4 py-2 font-bold">Upload Video</Button>
        </DialogTrigger>
        <DialogContent className="max-w-4xl">
          <DialogTitle>Upload Video</DialogTitle>
          <DialogDescription>
            Use the form below to upload your video and thumbnail.
          </DialogDescription>
          <VideoUpload onUploadSuccess={handleClose} />
        </DialogContent>
      </Dialog>
    </div>
  )
}

export default VideoUploadButton
