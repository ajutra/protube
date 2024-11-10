export interface VideoPreviewData {
    meta: any;
    videoFileName: string;
    thumbnailFileName: string;
    title: string;
    username: string;
}

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
  
export interface Video {
    videoFileName: string;
    title: string;
    meta: {
      tags: Tag[];
      categories: Category[];
      comments: Comment[];
    };
  }
  
export interface VideoDetailsProps {
    video: Video;
    onBack: () => void;
  }