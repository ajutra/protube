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
  const [, setVideoMetadata] = useState<VideoMetadata | null>(null)
  const [title, setTitle] = useState<string>('')
  const [description, setDescription] = useState<string>('')

  const onDropVideo = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0]
    if (file && ['video/mp4', 'video/webm', 'video/ogg'].includes(file.type)) {
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
      setUploadStatus('Please select a valid video file (MP4, WebM, Ogg).')
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
      setUploadStatus('')
    } else {
      setUploadStatus(
        'Please select a valid image file (JPEG, PNG, GIF, WebP, AVIF).'
      )
    }
  }, [])

  const handleUpload = async () => {
    if (!videoFile || !thumbnailFile || !title || !description) {
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

      // Crear un StoreVideoCommand
      const storeVideoCommand = {
        width: 640, // Proporciona los valores reales
        height: 480, // Proporciona los valores reales
        duration: 10, // Proporciona los valores reales
        title: title,
        description: description,
        username: username || 'Unknown User',
        videoFileName: videoFile.name,
        thumbnailFileName: thumbnailFile.name,
        tags: [],
        categories: [],
        comments: [],
      }

      // Añadir el StoreVideoCommand al formData
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
    setTitle,
    setDescription,
    onDropVideo,
    onDropThumbnail,
    handleUpload,
  }
}
