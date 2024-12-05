import { renderHook } from '@testing-library/react'
import { useProgressColor } from './useProgressColor'

describe('useProgressColor', () => {
  test('should return "bg-primary" for null value', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(null)
    expect(color).toBe('bg-primary')
  })

  test('should return "bg-primary" for undefined value', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(undefined)
    expect(color).toBe('bg-primary')
  })

  test('should return "red-500" for value less than 20', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(10)
    expect(color).toBe('red-500')
  })

  test('should return "orange-500" for value between 20 and 39', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(30)
    expect(color).toBe('orange-500')
  })

  test('should return "yellow-500" for value between 40 and 59', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(50)
    expect(color).toBe('yellow-500')
  })

  test('should return "green-500" for value between 60 and 79', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(70)
    expect(color).toBe('green-500')
  })

  test('should return "green-400" for value 80 and above', () => {
    const { result } = renderHook(() => useProgressColor())
    const color = result.current.getColor(80)
    expect(color).toBe('green-400')
  })
})
