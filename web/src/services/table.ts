import { api } from './api'

type GetTablesResponse = { id: string; name: string }[]

export async function getTablesRequest() {
  return api.get<GetTablesResponse>('/table/')
}

type GetTableRequest = {
  tableId: string
}

export type GetTableResponse = {
  id: string
  name: string
  urlQrCode: string
  status: string
  restaurant: {
    id: string
    name: string
  }
}

export async function getTableRequest({ tableId }: GetTableRequest) {
  return api.get<GetTableResponse>(`/table/${tableId}`)
}

type GetTableOrderRequest = GetTableRequest

type GetTableOrderResponse = {
  id: string
  items: {
    item: {
      description: string
      id: string
      image: string
      name: string
      price: number
    }
    id: string
    quantity: number
  }[]
  status: string
  tableId: string
  tableName: string
  userId: string
  userName: string
  restaurantName: string
}

export async function getTableOrderRequest({ tableId }: GetTableOrderRequest) {
  return api.get<GetTableOrderResponse>(`/table/${tableId}/order`)
}
