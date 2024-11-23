import React from 'react'
import { useDropzone } from 'react-dropzone'
import { Button } from '@/components/ui/button'
import { Progress } from '@/components/ui/progress'
import { Card, CardContent, CardTitle } from '@/components/ui/card'
import { useVideoUpload } from '../hooks/useVideoUpload'

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
  } = useVideoUpload(onUploadSuccess)

  const { getRootProps: getVideoRootProps, getInputProps: getVideoInputProps } =
    useDropzone({
      onDrop: onDropVideo,
      accept: { 'video/mp4': ['.mp4'] },
      multiple: false,
    })

  const {
    getRootProps: getThumbnailRootProps,
    getInputProps: getThumbnailInputProps,
  } = useDropzone({
    onDrop: onDropThumbnail,
    accept: { 'image/webp': ['.webp'] },
    multiple: false,
  })

  return (
    <Card className="mx-auto w-full max-w-2xl border-none shadow-lg">
      <CardContent>
        <CardTitle className="mb-4 text-center text-2xl font-bold">
          Upload Video
        </CardTitle>
        <div className="space-y-4">
          <div>
            <label className="mb-1 block text-sm font-medium">Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full rounded-md border bg-gray-100 shadow-sm focus:border-primary focus:ring-primary dark:bg-gray-700 dark:text-gray-100"
            />
          </div>
          <div>
            <label className="mb-1 block text-sm font-medium">
              Description
            </label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full rounded-md border bg-gray-100 shadow-sm focus:border-primary focus:ring-primary dark:bg-gray-700 dark:text-gray-100"
            />
          </div>
          <div>
            <div
              {...getVideoRootProps({
                className:
                  'dropzone border-dashed border-2 p-8 text-center h-64 flex items-center justify-center bg-gray-100 dark:bg-gray-700 dark:text-gray-100',
              })}
            >
              <input {...getVideoInputProps()} />
              {videoFile ? (
                <p>{videoFile.name}</p>
              ) : (
                <p>
                  Drag 'n' drop a MP4 video file here, or click to select one
                </p>
              )}
            </div>
            <p className="mt-2 text-sm dark:text-gray-300">
              Only MP4 files are allowed.
            </p>
          </div>
          <div>
            <div
              {...getThumbnailRootProps({
                className:
                  'dropzone border-dashed border-2 p-8 text-center h-64 flex items-center justify-center bg-gray-100 dark:bg-gray-700 dark:text-gray-100',
              })}
            >
              <input {...getThumbnailInputProps()} />
              {thumbnailFile ? (
                <p>{thumbnailFile.name}</p>
              ) : (
                <p>
                  Drag 'n' drop a WEBP thumbnail file here, or click to select
                  one
                </p>
              )}
            </div>
            <p className="mt-2 text-sm dark:text-gray-300">
              Only WEBP files are allowed.
            </p>
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
            <div className="mt-4 text-center">
              <p className="text-sm dark:text-gray-300">{uploadStatus}</p>
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  )
}

export default VideoUpload
