import { renderHook, act } from '@testing-library/react'
import useVideoPreviewHover from './useVideoPreviewHover'

jest.useFakeTimers()

describe('useVideoPreviewHover', () => {
  test('should initialize with isHovered as false', () => {
    const { result } = renderHook(() => useVideoPreviewHover())
    expect(result.current.isHovered).toBe(false)
  })

  test('should set isHovered to true after 500ms on handleMouseEnter', () => {
    const { result } = renderHook(() => useVideoPreviewHover())

    act(() => {
      result.current.handleMouseEnter()
    })

    expect(result.current.isHovered).toBe(false)

    act(() => {
      jest.advanceTimersByTime(500)
    })

    expect(result.current.isHovered).toBe(true)
  })

  test('should clear timeout and set isHovered to false on handleMouseLeave', () => {
    const { result } = renderHook(() => useVideoPreviewHover())

    act(() => {
      result.current.handleMouseEnter()
    })

    act(() => {
      jest.advanceTimersByTime(500)
    })

    expect(result.current.isHovered).toBe(true)

    act(() => {
      result.current.handleMouseLeave()
    })

    expect(result.current.isHovered).toBe(false)
  })

  test('should clear existing timeout on handleMouseLeave before 500ms', () => {
    const { result } = renderHook(() => useVideoPreviewHover())
    const clearTimeoutSpy = jest.spyOn(global, 'clearTimeout')

    act(() => {
      result.current.handleMouseEnter()
    })

    act(() => {
      jest.advanceTimersByTime(300)
    })

    act(() => {
      result.current.handleMouseLeave()
    })

    expect(result.current.isHovered).toBe(false)
    expect(clearTimeoutSpy).toHaveBeenCalled()
  })
})
