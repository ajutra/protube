import React, { useRef, useState } from 'react'
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
import { Loader2 } from 'lucide-react'
import { useToast } from '@/hooks/use-toast'

export function RegisterForm({ onRegister }: { onRegister: () => void }) {
  const { register } = useAuth()
  const { toast } = useToast()
  const usernameRef = useRef<HTMLInputElement>(null)
  const pwdRef = useRef<HTMLInputElement>(null)
  const pwdCheckRef = useRef<HTMLInputElement>(null)
  const [error, setError] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)

    if (pwdRef.current && pwdCheckRef.current) {
      if (pwdRef.current.value !== pwdCheckRef.current.value) {
        setError('Passwords do not match')
      } else if (usernameRef.current && pwdRef.current) {
        setIsLoading(true)
        const result = await register(
          usernameRef.current.value,
          pwdRef.current.value
        )
        if (result.error) {
          setError(result.error)
        } else {
          toast({
            description: 'You have been registered and logged in successfully',
          })
          onRegister()
        }
        setIsLoading(false)
      }
    }
  }

  return (
    <Card className="mx-auto max-w-sm border-none shadow-none">
      <CardHeader>
        <CardTitle className="text-2xl">Register</CardTitle>
        <CardDescription>
          Enter your username and password below to sign up for an account
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
            <Label htmlFor="password">Password</Label>
            <Input id="password" type="password" ref={pwdRef} required />
          </div>
          <div className="grid gap-2">
            <Label htmlFor="passwordCheck">Confirm Password</Label>
            <Input
              id="passwordCheck"
              type="password"
              ref={pwdCheckRef}
              required
            />
          </div>
          {error && (
            <CardDescription className="text-destructive">
              {error}
            </CardDescription>
          )}
          <Button type="submit" className="w-full" disabled={isLoading}>
            {isLoading && <Loader2 className="mr-2 animate-spin" />}
            Register
          </Button>
        </form>
      </CardContent>
    </Card>
  )
}
