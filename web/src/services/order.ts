import { api } from './api'

type CreateOrderRequest = { tableId: string }

export type PostOrderResponse = {
  id: string
  items: {
    item: {
      description: string
      id: string
      image: string
      name: string
      price: number
    }
    quantity: number
  }[]
  status: string
  tableId: string
  userId: string
}

export async function createOrderRequest({ tableId }: CreateOrderRequest) {
  return api.post<PostOrderResponse>('/order', { tableId })
}

type EditOrderRequest = {
  orderId: string
  items: { item: { id: string }; quantity: number }[]
}

// type EditOrderResponse = {  }

export async function editOrderRequest({ orderId, items }: EditOrderRequest) {
  return api.put(`/order/${orderId}`, { items })
}

export async function payOrderRequest({ orderId }: { orderId: string }) {
  return api.post(`/order/${orderId}/pay`)
}
