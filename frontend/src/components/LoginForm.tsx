import React, { useRef } from 'react'
import { Link } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { useAuth } from '@/context/AuthContext'

export function LoginForm({ onLogin }: { onLogin: () => void }) {
  const { login } = useAuth()
  const usernameRef = useRef<HTMLInputElement>(null)

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()

    if (usernameRef.current) {
      login(usernameRef.current.value)
      onLogin()
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
            <Label htmlFor="email">Username</Label>
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
              <Link to="#" className="ml-auto inline-block text-sm underline">
                Forgot your password?
              </Link>
            </div>
            <Input id="password" type="password" required />
          </div>
          <Button type="submit" className="w-full">
            Login
          </Button>
          <Button variant="outline" className="w-full">
            Login with Google
          </Button>
        </form>
        <div className="mt-4 text-center text-sm">
          Don&apos;t have an account?{' '}
          <Link to="#" className="underline">
            Sign up
          </Link>
        </div>
      </CardContent>
    </Card>
  )
}
