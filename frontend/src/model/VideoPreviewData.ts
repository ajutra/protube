import { Comment } from './Comment'

export interface VideoPreviewData {
  videoId: string
  videoFileName: string
  thumbnailFileName: string
  title: string
  username: string
  likes?: number
  dislikes?: number
  meta?: {
    description?: string
    tags?: { tagName: string }[]
    categories?: { categoryName: string }[]
    comments?: Comment[]
  }
}
