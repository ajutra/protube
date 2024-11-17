import React from 'react'
import Header from '../components/Header'

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
    <div>
      <Header />
      <div className="pt-16">
        <main>{children}</main>
      </div>
    </div>
  )
}

export default Layout
