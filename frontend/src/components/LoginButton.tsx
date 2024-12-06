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
import { RegisterForm } from '@/components/RegisterForm'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'
import { useState } from 'react'

export function LoginButton() {
  const [loginOpen, setLoginOpen] = useState(false)
  const [registerOpen, setRegisterOpen] = useState(false)

  const handleOpenRegister = () => {
    setLoginOpen(false)
    setRegisterOpen(true)
  }

  return (
    <>
      <Dialog open={loginOpen} onOpenChange={setLoginOpen}>
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
          <LoginForm
            onLogin={() => setLoginOpen(false)}
            onOpenRegister={handleOpenRegister}
          />
        </DialogContent>
      </Dialog>

      <Dialog open={registerOpen} onOpenChange={setRegisterOpen}>
        <DialogContent>
          <VisuallyHidden.Root>
            <DialogTitle>Sign up</DialogTitle>
            <DialogDescription>
              Enter your username and password below to sign up for an account
            </DialogDescription>
          </VisuallyHidden.Root>
          <RegisterForm onRegister={() => setRegisterOpen(false)} />
        </DialogContent>
      </Dialog>
    </>
  )
}
