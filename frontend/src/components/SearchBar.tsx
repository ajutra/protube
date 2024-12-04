import React, { useState, useRef, useEffect } from 'react'
import { Search, X } from 'lucide-react'
import { getEnv } from '@/utils/Env'
import { AppRoutes } from '@/enums/AppRoutes'
import { Link } from 'react-router-dom'
import { Button } from '@/components/ui/button'

interface SearchResult {
  id: string
  title: string
}

const SearchBar: React.FC = () => {
  const [isSearching, setIsSearching] = useState(false)
  const [searchResults, setSearchResults] = useState<SearchResult[]>([])
  const inputRef = useRef<HTMLInputElement>(null)
  const searchTimeoutRef = useRef<NodeJS.Timeout | null>(null)
  const searchResultsRef = useRef<HTMLDivElement>(null)

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

  useEffect(() => {
    if (isSearching) {
      const handleInputChange = () => {
        if (inputRef.current) {
          const query = inputRef.current.value
          if (searchTimeoutRef.current) {
            clearTimeout(searchTimeoutRef.current)
          }
          searchTimeoutRef.current = setTimeout(() => {
            handleSearch(query)
          }, 200)
        }
      }

      const inputElement = inputRef.current
      inputElement?.addEventListener('input', handleInputChange)

      return () => {
        inputElement?.removeEventListener('input', handleInputChange)
        if (searchTimeoutRef.current) {
          clearTimeout(searchTimeoutRef.current)
        }
      }
    }
  }, [isSearching])

  const handleMouseDown = (event: React.MouseEvent<HTMLDivElement>) => {
    if (
      searchResultsRef.current &&
      searchResultsRef.current.contains(event.target as Node)
    ) {
      event.preventDefault()
    }
  }

  const clearInput = () => {
    if (inputRef.current) {
      inputRef.current.value = ''
    }
    setSearchResults([])
    inputRef.current?.focus()
  }

  const handleLinkClick = () => {
    setIsSearching(false)
    if (inputRef.current) {
      inputRef.current.blur()
    }
  }

  return (
    <div className="relative w-full rounded-full">
      <div className={`absolute left-0 top-0 flex h-full items-center pl-5`}>
        {isSearching && <Search className="h-5 w-5" />}
      </div>
      <input
        ref={inputRef}
        placeholder="Search"
        onFocus={() => setIsSearching(true)}
        onBlur={(e) => {
          if (!searchResultsRef.current?.contains(e.relatedTarget)) {
            setIsSearching(false)
          }
        }}
        className={`w-full rounded-full border bg-background px-3 py-2 text-sm placeholder:text-muted-foreground focus:border-primary focus:outline-none ${isSearching ? 'pl-14' : 'pl-5'}`}
      />
      {inputRef.current?.value && (
        <Button
          onClick={clearInput}
          variant="ghost"
          size="icon"
          className="absolute right-0 top-0 mr-2 rounded-full"
        >
          <X />
        </Button>
      )}
      {isSearching && inputRef.current?.value && (
        <div
          ref={searchResultsRef}
          className="absolute left-0 right-0 top-full z-50 mt-2 max-h-96 w-full overflow-y-auto rounded-xl bg-secondary"
          onMouseDown={handleMouseDown}
        >
          {searchResults.length === 0 ? (
            <div className="px-5 py-4 text-sm font-bold text-secondary-foreground">
              No results found.
            </div>
          ) : (
            <div className="py-3">
              {searchResults.map((result) => (
                <div key={result.id}>
                  <Link
                    to={AppRoutes.VIDEO_DETAILS + '?id=' + result.id}
                    className="z-50 flex flex-row items-center gap-4 px-5 py-2 text-sm text-secondary-foreground hover:bg-background"
                    onClick={handleLinkClick}
                  >
                    <Search className="h-5 w-5" />
                    {result.title}
                  </Link>
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  )
}

export default SearchBar
