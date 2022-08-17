import { api } from './api'

type SignInRequestData = {
  username: string
  password: string
}

export async function signInRequest(credentials: SignInRequestData) {
  const { data } = await api.post<{ token: string }>('/auth', {
    ...credentials
  })
  return data
}
