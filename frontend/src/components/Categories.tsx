import React from 'react'
import { Badge } from './ui/badge'
import { cn } from '@/lib/utils'

interface CategoriesProps {
  categories: { categoryName: string }[]
  className?: string
  badgeClassName?: string
}

const Categories: React.FC<CategoriesProps> = ({
  categories,
  className,
  badgeClassName,
}) => {
  return (
    <div className={className}>
      {categories.length > 0 &&
        categories.map((category) => (
          <Badge
            key={category.categoryName}
            className={cn('cursor-default rounded-full', badgeClassName)}
          >
            {category.categoryName}
          </Badge>
        ))}
    </div>
  )
}

export default Categories
