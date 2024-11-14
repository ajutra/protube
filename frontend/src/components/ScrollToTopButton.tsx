import React, { useState, useEffect } from 'react';
import { FaArrowUp } from 'react-icons/fa';

const ScrollToTopButton: React.FC = () => {
  const [isVisible, setIsVisible] = useState(false);

  const toggleVisibility = () => {
    if (window.pageYOffset > 100) {
      setIsVisible(true);
    } else {
      setIsVisible(false);
    }
  };

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  useEffect(() => {
    window.addEventListener('scroll', toggleVisibility);
    return () => {
      window.removeEventListener('scroll', toggleVisibility);
    };
  }, []);

  return (
    <div className="fixed bottom-4 right-4 z-50">
      {isVisible && (
        <button
          type="button"
          className="bg-custom-gray text-white rounded-full p-2 shadow-lg hover:bg-custom-gray-dark focus:outline-none"
          onClick={scrollToTop}
          aria-label="Scroll to top"
        >
          <FaArrowUp className="w-3 h-3" />
        </button>
      )}
    </div>
  );
};

export default ScrollToTopButton;