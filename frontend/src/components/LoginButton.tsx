import { CircleUserRound } from 'lucide-react'
import { Button } from '@/components/ui/button'
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
} from '@/components/ui/dialog'
import { LoginForm } from '@/components/LoginForm'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'
import { useState } from 'react'

export function LoginButton() {
  const [open, setOpen] = useState(false)

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">
          <CircleUserRound />
          <span>Log In</span>
        </Button>
      </DialogTrigger>
      <DialogContent>
        <VisuallyHidden.Root>
          <DialogTitle>Login</DialogTitle>
          <DialogDescription>
            Enter your username and password below to login to your account
          </DialogDescription>
        </VisuallyHidden.Root>
        <LoginForm onLogin={() => setOpen(false)} />
      </DialogContent>
    </Dialog>
  )
}
