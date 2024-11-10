import React from 'react';
import { VideoPreviewData, Category, Comment, Tag } from '../model/VideoPreviewData';
import { getEnv } from '../utils/Env';
import 'bootstrap/dist/css/bootstrap.min.css';

interface VideoDetailsProps {
  video: VideoPreviewData;
  onBack: () => void;
  buttonColor?: string;
}

const VideoDetails: React.FC<VideoDetailsProps> = ({ video, onBack }) => {
  const commentCount = video.meta.comments ? video.meta.comments.length : 0;

  return (
    <div className="container pt-4">
      <div className="d-flex justify-content-start mb-2">
        <button
          className="btn btn-primary"
          onClick={onBack}
          style={{ backgroundColor: 'transparent', color: '#ffffff' }}
        >
          ←
        </button>
      </div>
      <div className="row mt-5">
        <div className="col">
          <video
            data-testid="video-element"
            controls
            src={`${getEnv().MEDIA_BASE_URL}/${video.videoFileName}`}
            className="w-100"
            style={{ maxWidth: '90vw', height: 'auto' }}
          />
        </div>
      </div>
      <div className="details mt-3">
        <h2 className="text-white bg-dark p-3 rounded">{video.title}</h2>
        <div className="tags mt-4">
          <h4 className="text-secondary border-bottom pb-2 text-start">TAGS:</h4>
          <div className="d-flex flex-wrap">
            {video.meta.tags && video.meta.tags.length > 0 ? (
              video.meta.tags.map((tag: Tag) => (
                <span key={tag.tagName} className="badge bg-secondary me-2 mb-2 fs-5">{tag.tagName}</span>
              ))
            ) : (
              <p className="text-muted" style={{ fontSize: '1.5rem', color: '#d3d3d3' }}>No tags available</p>
            )}
          </div>
        </div>
        <div className="categories mt-4">
          <h4 className="text-secondary border-bottom pb-2 text-start">CATEGORIES:</h4>
          <div className="d-flex flex-wrap">
            {video.meta.categories && video.meta.categories.length > 0 ? (
              video.meta.categories.map((category: Category) => (
                <span key={category.categoryName} className="badge bg-info me-2 mb-2 fs-5">{category.categoryName}</span>
              ))
            ) : (
              <p className="text-muted" style={{ fontSize: '1.5rem', color: '#d3d3d3' }}>No categories available</p>
            )}
          </div>
        </div>
        <div className="comments mt-4">
          <h4 className="text-secondary border-bottom pb-2 text-start">{commentCount} COMMENTS:</h4>
          {video.meta.comments && video.meta.comments.length > 0 ? (
            video.meta.comments.map((comment: Comment) => (
              <div key={comment.videoId} className="comment mb-2 p-3 border rounded bg-secondary w-100">
                <strong className="fs-5 text-start d-block">{comment.username}</strong>
                <p className="mb-0 fs-6 text-start">{comment.text}</p>
              </div>
            ))
          ) : (
            <p className="text-muted" style={{ fontSize: '1.5rem', color: '#d3d3d3' }}>No comments available</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default VideoDetails;