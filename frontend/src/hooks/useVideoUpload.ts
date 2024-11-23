import { useState, useCallback } from 'react'
import { getEnv } from '../utils/Env'
import { useAuth } from '@/context/AuthContext'

interface VideoMetadata {
  duration: number
  width: number
  height: number
}

interface UseVideoUploadResult {
  videoFile: File | null
  thumbnailFile: File | null
  uploadProgress: number
  uploadStatus: string
  title: string
  description: string
  setTitle: (title: string) => void
  setDescription: (description: string) => void
  onDropVideo: (acceptedFiles: File[]) => void
  onDropThumbnail: (acceptedFiles: File[]) => void
  handleUpload: () => Promise<void>
}

export const useVideoUpload = (
  onUploadSuccess: () => void
): UseVideoUploadResult => {
  const { API_BASE_URL } = getEnv()
  const { username } = useAuth()
  const [videoFile, setVideoFile] = useState<File | null>(null)
  const [thumbnailFile, setThumbnailFile] = useState<File | null>(null)
  const [uploadProgress, setUploadProgress] = useState<number>(0)
  const [uploadStatus, setUploadStatus] = useState<string>('')
  const [videoMetadata, setVideoMetadata] = useState<VideoMetadata | null>(null)
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
      if (error instanceof Error) {
        setUploadStatus(`An error occurred: ${error.message}`)
      } else {
        setUploadStatus('An unknown error occurred')
      }
    }
  }

  return {
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
  }
}
