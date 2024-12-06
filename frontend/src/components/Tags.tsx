import React from 'react'
import { Badge } from './ui/badge'

interface TagsProps {
  tags: { tagName: string }[]
}

const Tags: React.FC<TagsProps> = ({ tags }) => {
  return (
    <div className="flex flex-wrap gap-2">
      {tags.length > 0 &&
        tags.map((tag, index) => (
          <Badge
            key={index}
            variant="outline"
            className="cursor-default rounded-full bg-background text-sm"
          >
            {tag.tagName}
          </Badge>
        ))}
    </div>
  )
}

export default Tags
