import { renderHook, act } from '@testing-library/react'
import useScrollToTop from './useScrollToTop'

describe('useScrollToTop', () => {
  it('should initialize with showScroll as false', () => {
    const { result } = renderHook(() => useScrollToTop())
    expect(result.current.showScroll).toBe(false)
  })

  it('should set showScroll to true when scrolled down', () => {
    const { result } = renderHook(() => useScrollToTop())

    act(() => {
      window.pageYOffset = 400
      window.dispatchEvent(new Event('scroll'))
    })

    expect(result.current.showScroll).toBe(true)
  })

  it('should set showScroll to false when scrolled up', () => {
    const { result } = renderHook(() => useScrollToTop())

    act(() => {
      window.pageYOffset = 400
      window.dispatchEvent(new Event('scroll'))
    })

    expect(result.current.showScroll).toBe(true)

    act(() => {
      window.pageYOffset = 0
      window.dispatchEvent(new Event('scroll'))
    })

    expect(result.current.showScroll).toBe(false)
  })

  it('should scroll to top when scrollTop is called', () => {
    const { result } = renderHook(() => useScrollToTop())

    act(() => {
      result.current.scrollTop()
    })

    expect(window.scrollY).toBe(0)
  })
})
