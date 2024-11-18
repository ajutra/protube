import React from 'react'
import Header from '../components/Header'
import { Toaster } from '@/components/ui/toaster'

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
    <div>
      <Header />
      <div className="pt-16">
        <main>{children}</main>
        <Toaster />
      </div>
    </div>
  )
}

export default Layout
