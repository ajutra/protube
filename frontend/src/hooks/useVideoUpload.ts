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
  videoMetadata: VideoMetadata | null
  videoError: string
  thumbnailError: string
  setTitle: (title: string) => void
  setDescription: (description: string) => void
  onDropVideo: (acceptedFiles: File[]) => void
  onDropThumbnail: (acceptedFiles: File[]) => void
  handleUpload: () => Promise<void>
  setVideoFile: (file: File | null) => void
  validateVideoMetadata: (metadata: VideoMetadata) => boolean
  setVideoMetadata: (metadata: VideoMetadata | null) => void
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
  const [videoError, setVideoError] = useState<string>('')
  const [thumbnailError, setThumbnailError] = useState<string>('')

  const validateVideoMetadata = (metadata: VideoMetadata): boolean => {
    if (metadata.width < 640 || metadata.width > 7680) {
      setVideoError('Video width must be between 640 and 7680.')
      return false
    }
    if (metadata.height < 480 || metadata.height > 4320) {
      setVideoError('Video height must be between 480 and 4320.')
      return false
    }
    if (metadata.duration <= 0) {
      setVideoError('Video duration must be a positive number.')
      return false
    }
    return true
  }

  const onDropVideo = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0]
    if (file && ['video/mp4', 'video/webm', 'video/ogg'].includes(file.type)) {
      setVideoFile(file)
      setVideoError('')
      setUploadStatus('')

      const videoElement = document.createElement('video')
      videoElement.src = URL.createObjectURL(file)
      videoElement.onloadedmetadata = () => {
        const metadata: VideoMetadata = {
          duration: videoElement.duration,
          width: videoElement.videoWidth,
          height: videoElement.videoHeight,
        }

        if (validateVideoMetadata(metadata)) {
          setVideoMetadata(metadata)
        } else {
          setVideoFile(null)
          setVideoMetadata(null)
        }
      }
    } else {
      setVideoError('Please select a valid video file (MP4, WebM, Ogg).')
    }
  }, [])

  const onDropThumbnail = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0]
    if (
      file &&
      [
        'image/jpeg',
        'image/png',
        'image/gif',
        'image/webp',
        'image/avif',
      ].includes(file.type)
    ) {
      setThumbnailFile(file)
      setThumbnailError('')
      setUploadStatus('')
    } else {
      setThumbnailError(
        'Please select a valid image file (JPEG, PNG, GIF, WebP, AVIF).'
      )
    }
  }, [])

  const handleUpload = async () => {
    if (!videoFile || !thumbnailFile || !title || !videoMetadata) {
      setUploadStatus(
        'Please fill in all fields and select both a video file and a thumbnail file.'
      )
      return
    }

    setUploadStatus('Uploading...')
    setUploadProgress(0)

    try {
      const formData = new FormData()
      formData.append('file', videoFile)
      formData.append('thumbnail', thumbnailFile)

      const storeVideoCommand = {
        width: videoMetadata.width,
        height: videoMetadata.height,
        duration: videoMetadata.duration,
        title: title,
        description: description,
        username: username || 'Unknown User',
        videoFileName: videoFile.name,
        thumbnailFileName: thumbnailFile.name,
        tags: [],
        categories: [],
        comments: [],
      }

      formData.append(
        'storeVideoCommand',
        new Blob([JSON.stringify(storeVideoCommand)], {
          type: 'application/json',
        })
      )

      const response = await fetch(`${API_BASE_URL}/videos`, {
        method: 'POST',
        body: formData,
      })

      if (response.ok) {
        setUploadProgress(100)
        setUploadStatus('Upload successful!')
        onUploadSuccess()
      } else {
        const errorText = await response.text()
        setUploadStatus(`Upload failed: ${errorText}`)
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
    videoMetadata,
    setTitle,
    setDescription,
    onDropVideo,
    onDropThumbnail,
    handleUpload,
    validateVideoMetadata,
    setVideoFile,
    setVideoMetadata,
    videoError,
    thumbnailError,
  }
}
