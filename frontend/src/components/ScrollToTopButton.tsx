import React from 'react'
import { Button } from '@/components/ui/button'
import { ArrowUp } from 'lucide-react'
import useScrollToTop from '@/hooks/useScrollToTop'

const ScrollToTopButton: React.FC = () => {
  const { showScroll, scrollTop } = useScrollToTop(300)

  return (
    <Button
      variant={showScroll ? 'default' : 'outline'}
      className={`fixed bottom-8 right-8 flex h-10 w-10 items-center justify-center rounded-full shadow-md transition-transform duration-300 ${
        showScroll ? 'scale-100' : 'scale-0'
      }`}
      onClick={scrollTop}
    >
      <ArrowUp className="h-6 w-6" />
    </Button>
  )
}

export default ScrollToTopButton
