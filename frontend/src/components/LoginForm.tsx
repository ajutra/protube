import React, { useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
} from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { useAuth } from '@/context/AuthContext'
import { Loader2 } from 'lucide-react'
import * as VisuallyHidden from '@radix-ui/react-visually-hidden'
import { RegisterForm } from '@/components/RegisterForm'
import { useToast } from '@/hooks/use-toast'

export function LoginForm({ onLogin }: { onLogin: () => void }) {
  const { login, isLoading } = useAuth()
  const usernameRef = useRef<HTMLInputElement>(null)
  const pwdRef = useRef<HTMLInputElement>(null)
  const [error, setError] = useState<string | null>(null)
  const [registerOpen, setRegisterOpen] = useState(false)
  const { toast } = useToast()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)

    if (usernameRef.current && pwdRef.current) {
      const result = await login(
        usernameRef.current.value,
        pwdRef.current.value
      )
      if (result.error) {
        setError(result.error)
      } else {
        toast({ description: 'You have been logged in successfully' })
        onLogin()
      }
    }
  }

  return (
    <Card className="mx-auto max-w-sm border-none shadow-none">
      <CardHeader>
        <CardTitle className="text-2xl">Login</CardTitle>
        <CardDescription>
          Enter your username below to login to your account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="username">Username</Label>
            <Input
              id="username"
              type="text"
              placeholder="Your username"
              ref={usernameRef}
              required
            />
          </div>
          <div className="grid gap-2">
            <div className="flex items-center">
              <Label htmlFor="password">Password</Label>
            </div>
            <Input id="password" type="password" ref={pwdRef} required />
          </div>
          {error && (
            <CardDescription className="text-destructive">
              {error}
            </CardDescription>
          )}
          <Button type="submit" className="w-full" disabled={isLoading}>
            {isLoading && <Loader2 className="mr-2 animate-spin" />}
            Log in
          </Button>
          <Button variant="outline" className="w-full" disabled={isLoading}>
            Log in with Google
          </Button>
        </form>
        <div className="mt-4 text-center text-sm">
          Don&apos;t have an account?{' '}
          <Dialog open={registerOpen} onOpenChange={setRegisterOpen}>
            <DialogTrigger asChild>
              <Link to="#" className="underline">
                Sign up
              </Link>
            </DialogTrigger>
            <DialogContent>
              <VisuallyHidden.Root>
                <DialogTitle>Sign up</DialogTitle>
                <DialogDescription>
                  Enter your username and password below to sign up for an
                  account
                </DialogDescription>
              </VisuallyHidden.Root>
              <RegisterForm onRegister={() => setRegisterOpen(false)} />
            </DialogContent>
          </Dialog>
        </div>
      </CardContent>
    </Card>
  )
}
