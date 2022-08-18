import { api } from './api'

export type GetItemsResponse = {
  id: string
  image: string
  name: string
  description: string
  price: number
}[]

export async function getItemsRequest() {
  return api.get<GetItemsResponse>('/item')
}
