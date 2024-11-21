import React from 'react'
import { Badge } from './ui/badge'
import { Card, CardContent, CardTitle } from './ui/card'
import { Separator } from './ui/separator'

const Categories: React.FC<{ categories: { categoryName: string }[] }> = ({
  categories,
}) => {
  return (
    <Card className="p-4">
      <CardContent>
        <CardTitle className="text-left text-2xl font-bold">
          CATEGORIES
        </CardTitle>
        <Separator />
        <div className="flex flex-wrap">
          {categories.length > 0 ? (
            categories.map((category) => (
              <Badge
                key={category.categoryName}
                className="m-2 rounded-full px-4 py-2"
              >
                {category.categoryName}
              </Badge>
            ))
          ) : (
            <p className="text-sm">No categories available</p>
          )}
        </div>
      </CardContent>
    </Card>
  )
}

export default Categories
