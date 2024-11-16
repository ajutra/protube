import { useState, useCallback } from 'react'

const useVideoPreviewHover = () => {
    const [isHovered, setIsHovered] = useState(false)
    const [hoverTimeout, setHoverTimeout] = useState<NodeJS.Timeout | null>(
        null
    )

    const handleMouseEnter = useCallback(() => {
        const timeout = setTimeout(() => {
            setIsHovered(true)
        }, 500)
        setHoverTimeout(timeout)
    }, [])

    const handleMouseLeave = useCallback(() => {
        if (hoverTimeout) {
            clearTimeout(hoverTimeout)
            setHoverTimeout(null)
        }
        setIsHovered(false)
    }, [hoverTimeout])

    return { isHovered, handleMouseEnter, handleMouseLeave }
}

export default useVideoPreviewHover
