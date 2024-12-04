import React from 'react'
import { Search, X } from 'lucide-react'
import { AppRoutes } from '@/enums/AppRoutes'
import { Link } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import useSearchBar from '@/hooks/useSearchBar'

const SearchBar: React.FC = () => {
  const inputRef = React.useRef<HTMLInputElement>(null)
  const {
    inputValue,
    isSearching,
    searchResults,
    searchResultsRef,
    handleInputChange,
    handleMouseDown,
    clearInput,
    handleLinkClick,
    setIsSearching,
  } = useSearchBar()

  return (
    <div className="relative w-full rounded-full">
      <div className={`absolute left-0 top-0 flex h-full items-center pl-5`}>
        {isSearching && <Search className="h-5 w-5" />}
      </div>
      <input
        ref={inputRef}
        value={inputValue}
        placeholder="Search"
        onChange={handleInputChange}
        onFocus={() => setIsSearching(true)}
        onBlur={(e) => {
          if (!searchResultsRef.current?.contains(e.relatedTarget)) {
            setIsSearching(false)
          }
        }}
        className={`w-full rounded-full border bg-background px-3 py-2 text-sm placeholder:text-muted-foreground focus:border-primary focus:outline-none ${isSearching ? 'pl-14' : 'pl-5'}`}
      />
      {inputValue && (
        <Button
          onClick={() => {
            clearInput()
            inputRef.current?.focus()
          }}
          variant="ghost"
          size="icon"
          className="absolute right-0 top-0 mr-2 rounded-full"
        >
          <X />
        </Button>
      )}
      {isSearching && inputValue && (
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
                    onClick={() => {
                      handleLinkClick()
                      inputRef.current?.blur()
                    }}
                  >
                    <Search className="h-5 w-5 flex-shrink-0" />
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
