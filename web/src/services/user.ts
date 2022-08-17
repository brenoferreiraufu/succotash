import { api } from './api'

type RegisterUserRequestData = {
  fullName: string
  username: string
  password: string
}

export async function registerUserRequest(user: RegisterUserRequestData) {
  return api.post('/user', {
    ...user,
    role: 'WORKER'
  })
}
