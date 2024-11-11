import React from 'react';
import { Tag } from '../model/VideoTypes'; 

interface TagsProps {
  tags: Tag[];
}

const Tags: React.FC<TagsProps> = ({ tags }) => {
  return (
    <div className="tags mt-4">
      <h4 className="text-secondary border-bottom pb-2 text-start">TAGS:</h4>
      <div className="d-flex flex-wrap">
        {tags.length > 0 ? (
          tags.map((tag) => (
            <span key={tag.tagName} className="badge bg-secondary me-2 mb-2 fs-5">{tag.tagName}</span>
          ))
        ) : (
          <p className="text-muted fs-5">No tags available</p>
        )}
      </div>
    </div>
  );
};

export default Tags;
