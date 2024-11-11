export interface Tag {
  tagName: string;
}

export interface Category {
  categoryName: string;
}

export interface Comment {
  videoId: string;
  username: string;
  text: string;
}

export interface VideoPreviewData {
  videoFileName: string;
  thumbnailFileName: string;
  title: string;
  username: string;
  meta: {
    tags: Tag[];
    categories: Category[];
    comments: Comment[];
  };
}