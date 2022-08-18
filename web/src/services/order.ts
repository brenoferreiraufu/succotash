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

export async function createOrderRequest({ tableId }: CreateOrderRequest) {
  return api.post<PostOrderResponse>('/order', { tableId })
}

type EditOrderRequest = {
  orderId: string
  items: ({ item: { id: string }; quantity: number } | { orderItemId: string; quantity: number })[]
}

export async function editOrderRequest({ orderId, items }: EditOrderRequest) {
  return api.put(`/order/${orderId}`, { items })
}

type RemoveOrderItemIdRequest = {
  orderItemId: string
}

export async function removeOrderItemRequest({ orderItemId }: RemoveOrderItemIdRequest) {
  return api.delete(`/order/${orderItemId}`)
}

export async function payOrderRequest({ orderId }: { orderId: string }) {
  return api.post(`/order/${orderId}/pay`)
}
