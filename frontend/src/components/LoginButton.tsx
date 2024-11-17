import { CircleUserRound } from 'lucide-react'
import { Button } from '@/components/ui/button'

export function LoginButton() {
  return (
    <Button variant="outline">
      <CircleUserRound />
      <span>Log In</span>
    </Button>
  )
}
