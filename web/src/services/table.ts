import { api } from './api'

type TableResponse = { id: string; name: string }[]

export async function getTablesRequest() {
  return api.get<TableResponse>('/table/')
}
