import React, { useState, useCallback } from 'react'
import { useDropzone } from 'react-dropzone'
import { Button } from '@/components/ui/button'
import { Progress } from '@/components/ui/progress'
import { Card, CardContent, CardTitle } from '@/components/ui/card'

const VideoUpload: React.FC = () => {
  const [videoFile, setVideoFile] = useState<File | null>(null)
  const [thumbnailFile, setThumbnailFile] = useState<File | null>(null)
  const [uploadProgress, setUploadProgress] = useState<number>(0)
  const [uploadStatus, setUploadStatus] = useState<string>('')

  const onDropVideo = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0]
    if (file && file.type === 'video/mp4') {
      setVideoFile(file)
      setUploadStatus('')
    } else {
      setUploadStatus('Please select a valid MP4 video file.')
    }
  }, [])

  const onDropThumbnail = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0]
    if (file && file.type === 'image/webp') {
      setThumbnailFile(file)
      setUploadStatus('')
    } else {
      setUploadStatus('Please select a valid WEBP thumbnail file.')
    }
  }, [])

  const { getRootProps: getVideoRootProps, getInputProps: getVideoInputProps } =
    useDropzone({
      onDrop: onDropVideo,
      accept: 'video/mp4',
      multiple: false,
    })

  const {
    getRootProps: getThumbnailRootProps,
    getInputProps: getThumbnailInputProps,
  } = useDropzone({
    onDrop: onDropThumbnail,
    accept: 'image/webp',
    multiple: false,
  })

  const handleUpload = async () => {
    if (!videoFile || !thumbnailFile) {
      setUploadStatus('Please select both a video file and a thumbnail file.')
      return
    }

    setUploadStatus('Uploading...')
    setUploadProgress(0)

    // Simulate upload process
    const uploadSimulation = setInterval(() => {
      setUploadProgress((prev) => {
        if (prev >= 100) {
          clearInterval(uploadSimulation)
          setUploadStatus('Upload successful!')
          return 100
        }
        return prev + 10
      })
    }, 300)
  }

  return (
    <Card className="mx-auto mt-24 w-full max-w-4xl shadow-lg">
      <CardContent>
        <CardTitle className="text-center text-2xl font-bold">
          Upload Video
        </CardTitle>
        <div className="mt-4">
          <div
            {...getVideoRootProps({
              className: 'dropzone border-dashed border-2 p-8 text-center h-64',
            })}
          >
            <input {...getVideoInputProps()} />
            {videoFile ? (
              <p>{videoFile.name}</p>
            ) : (
              <p>Drag 'n' drop a MP4 video file here, or click to select one</p>
            )}
          </div>
          <p className="mt-2 text-sm">Only MP4 files are allowed.</p>
        </div>
        <div className="mt-4">
          <div
            {...getThumbnailRootProps({
              className: 'dropzone border-dashed border-2 p-8 text-center h-64',
            })}
          >
            <input {...getThumbnailInputProps()} />
            {thumbnailFile ? (
              <p>{thumbnailFile.name}</p>
            ) : (
              <p>
                Drag 'n' drop a WEBP thumbnail file here, or click to select one
              </p>
            )}
          </div>
          <p className="mt-2 text-sm">Only WEBP files are allowed.</p>
        </div>
        <div className="mt-4">
          <Button onClick={handleUpload}>Upload</Button>
        </div>
        {uploadProgress > 0 && (
          <div className="mt-4">
            <Progress value={uploadProgress} />
          </div>
        )}
        {uploadStatus && (
          <div className="mt-4 text-center">
            <p className="text-sm">{uploadStatus}</p>
          </div>
        )}
      </CardContent>
    </Card>
  )
}

export default VideoUpload
