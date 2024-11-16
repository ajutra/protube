import { Comment } from './Comment';

export interface VideoPreviewData {
  videoFileName: string;
  thumbnailFileName: string;
  title: string;
  username: string;
  meta: {
    tags: { tagName: string }[];
    categories: { categoryName: string }[];
    comments: Comment[];
  };
}