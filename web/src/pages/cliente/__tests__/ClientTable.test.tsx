import { render, screen, waitFor } from 'test/test-utils'
import { mockedToastFn } from 'test/mocks/useToast'
import ClientTable from '../[tableId]'
import { apiMockAdapter } from 'test/mocks/api'

jest.mock('next/router', () => ({
  useRouter: () => ({
    push: jest.fn(),
    query: {
      tableId: '1'
    }
  })
}))

describe('<ClientTable />', () => {
  beforeEach(() => {
    apiMockAdapter.onGet('/table/1').reply(200, {
      id: '1',
      name: 'Table 7',
      urlQrCode: 'https://www.google.com/7',
      status: 'OPEN',
      restaurant: {
        id: '4cb42437-e7d4-4bab-9eba-73b0fef10259',
        name: 'Mock'
      }
    })
  })
  afterEach(() => {
    apiMockAdapter.reset()
  })
  it('should render correctly', async () => {
    render(<ClientTable />)
    expect(await screen.findByText('Restaurante Mock')).toBeInTheDocument()
    expect(screen.getByText('Table 7')).toBeInTheDocument()
  })
  it('should call a toast if the table resquest goes wrong', async () => {
    apiMockAdapter.onGet('/table/1').reply(400)
    render(<ClientTable />)
    await waitFor(() => {
      expect(mockedToastFn).toBeCalledWith({
        title: 'Falha ao carregar as informações da mesa',
        status: 'error',
        isClosable: true
      })
    })
  })
})
