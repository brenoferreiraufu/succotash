import { getAPIClient } from './axios'
import MockAdapter from 'axios-mock-adapter'

export const api = getAPIClient()

export const apiMockAdapter = new MockAdapter(api)
