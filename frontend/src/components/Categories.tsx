import React from 'react';
import { Category } from '../model/VideoPreviewData';

interface CategoriesProps {
  categories: Category[];
}

const Categories: React.FC<CategoriesProps> = ({ categories }) => {
  return (
    <div className="categories mt-4">
      <h4 className="text-secondary border-bottom pb-2 text-start">CATEGORIES:</h4>
      <div className="d-flex flex-wrap">
        {categories.length > 0 ? (
          categories.map((category) => (
            <span key={category.categoryName} className="badge bg-info me-2 mb-2 fs-5">{category.categoryName}</span>
          ))
        ) : (
          <p className="text-muted fs-5">No categories available</p>
        )}
      </div>
    </div>
  );
};

export default Categories;