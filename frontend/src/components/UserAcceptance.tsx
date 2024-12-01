import { Progress } from '@/components/ui/progress'
import { cn } from '@/lib/utils'
import { useProgressColor } from '@/hooks/useProgressColor'

interface UserAcceptanceProps {
  likes: number
  dislikes: number
}

const UserAcceptance = ({ likes, dislikes }: UserAcceptanceProps) => {
  const total = likes + dislikes
  const acceptance = total === 0 ? 50 : (likes / total) * 100
  const { getColor } = useProgressColor()

  return (
    <div className="flex max-h-8 w-full items-center space-x-2">
      <Progress value={acceptance} className="w-32" />
      <span className={cn(`font-bold text-${getColor(acceptance)}`)}>
        {acceptance.toFixed(0)}%
      </span>
    </div>
  )
}

export { UserAcceptance }
