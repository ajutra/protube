import React from 'react'
import { Badge } from './ui/badge'
import { Card, CardContent, CardTitle } from './ui/card'

const Tags: React.FC<{ tags: { tagName: string }[] }> = ({ tags }) => {
  return (
    <Card className="pt-4">
      <CardContent>
        <CardTitle className="border-b pb-2 text-left text-2xl font-bold">
          TAGS:
        </CardTitle>
        <div className="flex flex-wrap justify-center">
          {tags.length > 0 ? (
            tags.map((tag, index) => (
              <Badge
                key={index}
                variant="outline"
                className="m-2 rounded-full px-5 py-2"
              >
                {tag.tagName}
              </Badge>
            ))
          ) : (
            <p className="text-sm">No tags available</p>
          )}
        </div>
      </CardContent>
    </Card>
  )
}

export default Tags
