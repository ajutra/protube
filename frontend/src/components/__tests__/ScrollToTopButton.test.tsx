import { render, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import ScrollToTopButton from '../ScrollToTopButton';

describe('ScrollToTopButton', () => {
  it('renders the button when scrolled down', () => {
    const { getByRole } = render(<ScrollToTopButton />);
    window.scrollTo = jest.fn();
    window.pageYOffset = 200;
    fireEvent.scroll(window);
    expect(getByRole('button')).toBeInTheDocument();
  });

  it('does not render the   button when at the top', () => {
    const { queryByRole } = render(<ScrollToTopButton />);
    window.scrollTo = jest.fn();
    window.pageYOffset = 0;
    fireEvent.scroll(window);
    expect(queryByRole('button')).toBeNull();
  });

  it('scrolls to the top when clicked', () => {
    const { getByRole } = render(<ScrollToTopButton />);
    window.scrollTo = jest.fn();
    window.pageYOffset = 200;
    fireEvent.scroll(window);
    fireEvent.click(getByRole('button'));
    expect(window.scrollTo).toHaveBeenCalledWith({ top: 0, behavior: 'smooth' });
  });
});