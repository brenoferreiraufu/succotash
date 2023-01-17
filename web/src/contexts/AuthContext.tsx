import { createContext, useContext, useState } from 'react'
import { setCookie, destroyCookie } from 'nookies'
import Router from 'next/router'

import { CommonHeaderProperties } from 'services/axios'
import { api } from 'services/api'
import { signInRequest } from 'services/auth'

type SignInData = {
  username: string
  password: string
}

type AuthContextType = {
  isAuthenticated: boolean | null
  signIn: (data: SignInData) => Promise<void>
  logout: () => void
}

export const AuthContext = createContext({} as AuthContextType)

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null)

  async function signIn({ username, password }: SignInData) {
    const { token } = await signInRequest({
      username,
      password
    })

    setIsAuthenticated(true)

    setCookie(null, 'nextauth.token', token, {
      path: '/',
      maxAge: 60 * 60 * 1 // 1 hour
    })

    api.defaults.headers = {
      ...api.defaults.headers,
      Authorization: `Bearer ${token}`
    } as CommonHeaderProperties
  }

  function logout() {
    setIsAuthenticated(false)

    destroyCookie(null, 'nextauth.token', {
      path: '/'
    })

    api.defaults.headers = {
      ...api.defaults.headers,
      Authorization: ''
    } as CommonHeaderProperties

    Router.push('/')
  }

  return <AuthContext.Provider value={{ isAuthenticated, signIn, logout }}>{children}</AuthContext.Provider>
}

export function useAuthContext() {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuthProvider should be user within a AuthProvider')
  }
  return context
}
