import { createContext, useState } from 'react'
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

    setCookie(undefined, 'nextauth.token', token, {
      maxAge: 60 * 60 * 1 // 1 hour
    })

    api.defaults.headers = {
      ...api.defaults.headers,
      Authorization: `Bearer ${token}`
    } as CommonHeaderProperties
  }

  function logout() {
    setIsAuthenticated(false)

    destroyCookie(undefined, 'nextauth.token')

    api.defaults.headers = {
      ...api.defaults.headers,
      Authorization: ''
    } as CommonHeaderProperties

    Router.push('/')
  }

  return <AuthContext.Provider value={{ isAuthenticated, signIn, logout }}>{children}</AuthContext.Provider>
}
