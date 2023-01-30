import { render, screen, waitFor } from 'test/test-utils'
import userEvent from '@testing-library/user-event'
import { mockedToastFn } from 'test/mocks/useToast'
import ClientOrder from '../[tableId]'
import { apiMockAdapter } from 'test/mocks/api'

jest.mock('next/router', () => ({
  useRouter: () => ({
    back: jest.fn(),
    query: {
      tableId: '1'
    }
  })
}))

describe('<ClientOrder />', () => {
  beforeEach(() => {
    apiMockAdapter.onPost('order/order-id/pay').reply(200)
    apiMockAdapter.onGet('/table/1/order').reply(200, {
      id: 'order-id',
      userId: 'mock-user-id',
      userName: 'Teste-User',
      tableId: '1',
      tableName: 'Table 7',
      restaurantName: 'Mock',
      status: 'IN_PREPARATION',
      items: [
        {
          id: '3ee49961-edc5-4c88-8e8b-f1d853099d1d',
          quantity: 2,
          item: {
            id: 'd2f73734-6287-4fbf-bc57-f81cfbe53fcd',
            image: '17.jpg',
            name: 'Item 17',
            description: 'Description 17',
            price: 17
          }
        }
      ]
    })
  })
  afterEach(() => {
    apiMockAdapter.reset()
  })
  it('should render correctly', async () => {
    render(<ClientOrder />)
    expect(await screen.findByText('Restaurante Mock')).toBeInTheDocument()
    expect(screen.getByText('Table 7')).toBeInTheDocument()
    expect(screen.getByText('Garçom: Teste-User')).toBeInTheDocument()
    expect(screen.getByText('Item 17')).toBeInTheDocument()
  })
  it('should call a toast if the table resquest goes wrong', async () => {
    apiMockAdapter.onGet('/table/1/order').reply(400)
    const { unmount } = render(<ClientOrder />)
    await waitFor(() => {
      expect(mockedToastFn).toBeCalledWith({
        title: 'Falha ao carregar detalhes do pedido',
        status: 'error',
        isClosable: true
      })
    })

    unmount()

    apiMockAdapter.onGet('/table/1/order').reply(404)
    render(<ClientOrder />)
    await waitFor(() => {
      expect(mockedToastFn).toBeCalledWith({
        title: 'Atendimento não foi iniciado!',
        status: 'error'
      })
    })
  })
  it('should pay the order and after display a modal showing the order was paid', async () => {
    const user = userEvent.setup()
    render(<ClientOrder />)

    await user.click(
      screen.getByRole('button', {
        name: /pagar conta/i
      })
    )

    expect(await screen.findByRole('dialog')).toBeInTheDocument()
    expect(screen.getByText(/recebemos a confirmação do seu pagamento!/i)).toBeInTheDocument()
  })
})
