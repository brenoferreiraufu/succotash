import axios, { HeadersDefaults } from 'axios'
import { parseCookies } from 'nookies'

export interface CommonHeaderProperties extends HeadersDefaults {
  Authorization: string
}

export function getAPIClient(ctx?: any) {
  const { 'nextauth.token': token } = parseCookies(ctx)

  const api = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL
  })

  api.interceptors.request.use((config) => {
    console.log(config)

    return config
  })

  if (token) {
    api.defaults.headers = {
      ...api.defaults.headers,
      Authorization: `Bearer ${token}`
    } as CommonHeaderProperties
  }

  return api
}
