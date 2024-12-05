import React from 'react'
import { useDropzone } from 'react-dropzone'
import { Button } from '@/components/ui/button'
import { Progress } from '@/components/ui/progress'
import { Card, CardContent, CardTitle } from '@/components/ui/card'
import { useVideoUpload } from '../hooks/useVideoUpload'
import { LucideVideo, LucideImage, LucideText } from 'lucide-react'
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from '@/components/ui/alert-dialog'

interface VideoUploadProps {
  onUploadSuccess: () => void
}

const VideoUpload: React.FC<VideoUploadProps> = ({ onUploadSuccess }) => {
  const {
    videoFile,
    thumbnailFile,
    uploadProgress,
    uploadStatus,
    title,
    description,
    videoError,
    thumbnailError,
    fillAllFieldsError,
    showFillAllFieldsError,
    setTitle,
    setDescription,
    onDropVideo,
    onDropThumbnail,
    handleUpload,
    setShowFillAllFieldsError,
  } = useVideoUpload(onUploadSuccess)

  const { getRootProps: getVideoRootProps, getInputProps: getVideoInputProps } =
    useDropzone({
      onDrop: onDropVideo,
      accept: {
        'video/mp4': ['.mp4'],
        'video/webm': ['.webm'],
        'video/ogg': ['.ogg'],
      },
      multiple: false,
    })

  const {
    getRootProps: getThumbnailRootProps,
    getInputProps: getThumbnailInputProps,
  } = useDropzone({
    onDrop: onDropThumbnail,
    accept: {
      'image/jpeg': ['.jpg', '.jpeg'],
      'image/png': ['.png'],
      'image/gif': ['.gif'],
      'image/webp': ['.webp'],
      'image/avif': ['.avif'],
    },
    multiple: false,
  })

  return (
    <AlertDialog
      open={showFillAllFieldsError}
      onOpenChange={setShowFillAllFieldsError}
    >
      <Card className="mx-auto w-full max-w-3xl border-none shadow-none">
        <CardContent>
          <CardTitle className="mb-6 text-center text-3xl font-extrabold text-foreground">
            Upload a new video
          </CardTitle>
          <div className="space-y-6">
            <div>
              <label className="mb-1 block text-sm font-medium">
                <LucideText className="mr-2 inline-block text-primary" />
                Title
              </label>
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Enter video title"
                className="w-full rounded-lg border border-input bg-input px-4 py-2 placeholder-muted-foreground shadow-sm focus:border-primary focus:ring-primary"
              />
            </div>
            <div>
              <label className="mb-1 block text-sm font-medium">
                <LucideText className="mr-2 inline-block text-primary" />
                Description
              </label>
              <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                placeholder="Tell users what your video is about"
                className="w-full rounded-lg border border-input bg-input px-4 py-2 text-muted-foreground placeholder-muted-foreground shadow-sm focus:border-primary"
              />
            </div>
            <div>
              <label className="mb-1 block text-sm font-medium">
                <LucideVideo className="mr-2 inline-block text-primary" />
                Video File
              </label>
              <div
                {...getVideoRootProps({
                  className:
                    'dropzone border-dashed border-2 p-6 text-center h-48 flex items-center justify-center bg-input text-muted-foreground rounded-xl cursor-pointer',
                })}
              >
                <input {...getVideoInputProps()} />
                {videoFile ? (
                  <p>{videoFile.name}</p>
                ) : (
                  <p>Drag 'n' drop a video file here, or click to select one</p>
                )}
              </div>
              {videoError && (
                <p className="mt-2 text-sm text-destructive">{videoError}</p>
              )}
            </div>
            <div>
              <label className="mb-1 block text-sm font-medium">
                <LucideImage className="mr-2 inline-block text-primary" />
                Thumbnail Image
              </label>
              <div
                {...getThumbnailRootProps({
                  className:
                    'dropzone border-dashed border-2 p-6 text-center h-48 flex items-center justify-center bg-input text-muted-foreground rounded-xl cursor-pointer',
                })}
              >
                <input {...getThumbnailInputProps()} />
                {thumbnailFile ? (
                  <p>{thumbnailFile.name}</p>
                ) : (
                  <p>
                    Drag 'n' drop an image file here, or click to select one
                  </p>
                )}
              </div>
              {thumbnailError && (
                <p className="mt-2 text-sm text-destructive">
                  {thumbnailError}
                </p>
              )}
            </div>
            <div className="flex w-full justify-end">
              <Button onClick={handleUpload}>Upload</Button>
            </div>
            {uploadProgress > 0 && (
              <div className="mt-4">
                <Progress value={uploadProgress} className="h-2" />
              </div>
            )}
            {uploadStatus && (
              <div className="mt-4 text-center text-sm text-foreground">
                {uploadStatus}
              </div>
            )}
          </div>
        </CardContent>
      </Card>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>Error</AlertDialogTitle>
          <AlertDialogDescription>{fillAllFieldsError}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogAction>Continue</AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  )
}

export default VideoUpload
