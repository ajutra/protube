import { useState, useRef, useEffect } from 'react'
import { getEnv } from '@/utils/Env'

interface SearchResult {
  id: string
  title: string
}

const useSearchBar = () => {
  const [inputValue, setInputValue] = useState('')
  const [isSearching, setIsSearching] = useState(false)
  const [searchResults, setSearchResults] = useState<SearchResult[]>([])
  const searchTimeoutRef = useRef<NodeJS.Timeout | null>(null)
  const searchResultsRef = useRef<HTMLDivElement | null>(null)

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value)
    if (searchTimeoutRef.current) {
      clearTimeout(searchTimeoutRef.current)
    }
    searchTimeoutRef.current = setTimeout(() => {
      handleSearch(event.target.value)
    }, 200)
  }

  const handleSearch = async (query: string) => {
    if (!query.trim()) {
      setSearchResults([])
      return
    }
    const response = await fetch(
      getEnv().API_BASE_URL + `/videos/search/${query}`
    )
    const results: SearchResult[] = await response.json()
    setSearchResults(results)
  }

  const handleMouseDown = (event: React.MouseEvent<HTMLDivElement>) => {
    if (
      searchResultsRef.current &&
      searchResultsRef.current.contains(event.target as Node)
    ) {
      event.preventDefault()
    }
  }

  const clearInput = () => {
    setInputValue('')
    setSearchResults([])
  }

  const handleLinkClick = () => {
    setIsSearching(false)
  }

  useEffect(() => {
    return () => {
      if (searchTimeoutRef.current) {
        clearTimeout(searchTimeoutRef.current)
      }
    }
  }, [])

  return {
    inputValue,
    isSearching,
    searchResults,
    searchResultsRef,
    handleInputChange,
    handleMouseDown,
    clearInput,
    handleLinkClick,
    setIsSearching,
  }
}

export default useSearchBar
