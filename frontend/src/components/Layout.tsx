import React from 'react'
import Header from './Header'

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
