import { useCallback } from 'react'

export const useProgressColor = () => {
  const getColor = useCallback((value: number | null | undefined) => {
    if (value === undefined || value === null) return 'bg-primary'
    if (value < 20) return 'red-500'
    if (value < 40) return 'orange-500'
    if (value < 60) return 'yellow-500'
    if (value < 80) return 'green-500'
    return 'green-400'
  }, [])

  return { getColor }
}
