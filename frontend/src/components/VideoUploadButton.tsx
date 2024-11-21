import React, { useState } from 'react'
import { Button } from '@/components/ui/button'
import VideoUpload from '@/pages/VideoUpload'
import { Dialog, DialogTrigger, DialogContent } from '@/components/ui/dialog'

const VideoUploadButton: React.FC = () => {
  const [open, setOpen] = useState(false)

  return (
    <div>
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogTrigger asChild>
          <Button className="rounded bg-primary px-4 py-2 font-bold">
            Upload Video
          </Button>
        </DialogTrigger>
        <DialogContent className="max-w-4xl">
          <VideoUpload />
        </DialogContent>
      </Dialog>
    </div>
  )
}

export default VideoUploadButton
