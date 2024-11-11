interface Tag {
  tagName: string;
}

interface Category {
  categoryName: string;
}

interface Comment {
  videoId: string;
  username: string;
  text: string;
}

interface VideoPreviewData {
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