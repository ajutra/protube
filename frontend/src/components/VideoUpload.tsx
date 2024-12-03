import React from 'react'
import { useDropzone } from 'react-dropzone'
import { Button } from '@/components/ui/button'
import { Progress } from '@/components/ui/progress'
import { Card, CardContent, CardTitle } from '@/components/ui/card'
import { useVideoUpload } from '../hooks/useVideoUpload'
import { LucideVideo, LucideImage, LucideText } from 'lucide-react'

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
    setTitle,
    setDescription,
    onDropVideo,
    onDropThumbnail,
    handleUpload,
    videoError,
    thumbnailError,
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
    <Card className="mx-auto w-full max-w-3xl border-none shadow-none">
      <CardContent>
        <CardTitle className="mb-6 text-center text-3xl font-extrabold tracking-tight text-primary">
          Upload Video
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
              className="w-full rounded-lg border border-input bg-input px-4 py-2 shadow-sm focus:border-primary focus:ring-primary"
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
              className="w-full rounded-lg border border-input bg-input px-4 py-2 shadow-sm focus:border-primary focus:ring-primary"
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
                  'dropzone border-dashed border-2 p-6 text-center h-48 flex items-center justify-center bg-input text-foreground',
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
                  'dropzone border-dashed border-2 p-6 text-center h-48 flex items-center justify-center bg-input text-foreground',
              })}
            >
              <input {...getThumbnailInputProps()} />
              {thumbnailFile ? (
                <p>{thumbnailFile.name}</p>
              ) : (
                <p>Drag 'n' drop an image file here, or click to select one</p>
              )}
            </div>
            {thumbnailError && (
              <p className="mt-2 text-sm text-destructive">{thumbnailError}</p>
            )}
          </div>
          <div>
            <Button onClick={handleUpload} className="w-full py-3">
              Upload
            </Button>
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
  )
}

export default VideoUpload
