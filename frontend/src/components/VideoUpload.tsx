import React, { useState, useCallback } from 'react'
import { useDropzone } from 'react-dropzone'
import { Button } from '@/components/ui/button'
import { Progress } from '@/components/ui/progress'
import { Card, CardContent, CardTitle } from '@/components/ui/card'
import { getEnv } from '../utils/Env'
import { useAuth } from '@/context/AuthContext'

interface VideoUploadProps {
  onUploadSuccess: () => void
}

const VideoUpload: React.FC<VideoUploadProps> = ({ onUploadSuccess }) => {
  const { API_BASE_URL } = getEnv()
  const { username } = useAuth()
  const [videoFile, setVideoFile] = useState<File | null>(null)
  const [thumbnailFile, setThumbnailFile] = useState<File | null>(null)
  const [uploadProgress, setUploadProgress] = useState<number>(0)
  const [uploadStatus, setUploadStatus] = useState<string>('')
  const [videoMetadata, setVideoMetadata] = useState<{
    duration: number
    width: number
    height: number
  } | null>(null)
  const [title, setTitle] = useState<string>('')
  const [description, setDescription] = useState<string>('')

  const onDropVideo = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0]
    if (file && file.type === 'video/mp4') {
      setVideoFile(file)
      setUploadStatus('')

      // Extraer metadatos del video
      const videoElement = document.createElement('video')
      videoElement.src = URL.createObjectURL(file)
      videoElement.onloadedmetadata = () => {
        setVideoMetadata({
          duration: videoElement.duration,
          width: videoElement.videoWidth,
          height: videoElement.videoHeight,
        })
      }
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

  const handleUpload = async () => {
    if (
      !videoFile ||
      !thumbnailFile ||
      !videoMetadata ||
      !title ||
      !description
    ) {
      setUploadStatus(
        'Please fill in all fields and select both a video file and a thumbnail file.'
      )
      return
    }

    setUploadStatus('Uploading...')
    setUploadProgress(0)

    const formData = new FormData()
    formData.append('videoFile', videoFile)
    formData.append('thumbnailFile', thumbnailFile)
    formData.append('title', title)
    formData.append('description', description)
    formData.append('username', username || 'Unknown User')
    formData.append('duration', videoMetadata.duration.toString())
    formData.append('width', videoMetadata.width.toString())
    formData.append('height', videoMetadata.height.toString())

    try {
      const response = await fetch(`${API_BASE_URL}/videos/upload`, {
        method: 'POST',
        body: formData,
      })

      if (response.ok) {
        setUploadProgress(100)
        setUploadStatus('Upload successful!')
        onUploadSuccess()
      } else {
        const errorText = await response.text()
        try {
          const errorData = JSON.parse(errorText)
          setUploadStatus(`Upload failed: ${errorData.message}`)
        } catch (e) {
          setUploadStatus(`Upload failed: ${errorText}`)
        }
      }
    } catch (error) {
      setUploadStatus(`An error occurred: ${error.message}`)
    }
  }

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
