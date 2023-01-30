import { render, waitFor, screen } from 'test/test-utils'
import userEvent from '@testing-library/user-event'
import { apiMockAdapter } from 'test/mocks/api'
import RestaurantTable from '../[tableId]'

jest.mock('next/router', () => ({
  useRouter: () => ({
    query: {
      tableId: '1'
    }
  })
}))

describe('<RestaurantTable />', () => {
  const user = userEvent.setup()
  beforeEach(() => {
    apiMockAdapter.onPost('/order').reply(200, {
      id: 'mock-id',
      items: [
        {
          item: {
            description: 'mock-item-description',
            id: 'mock-item-id',
            image: 'mock-item-image',
            name: 'mock-item-name',
            price: 10
          },
          id: 'mock-items-id',
          quantity: 2
        }
      ],
      status: 'mock-status',
      tableId: 'mock-tableId',
      tableName: 'mock-tableName',
      userId: 'mock-userId',
      userName: 'mock-userName',
      restaurantName: 'mock-restaurantName'
    })
    apiMockAdapter.onGet('/item').reply(200, [
      {
        description: 'mock-item-description',
        id: 'mock-item-id',
        image: 'mock-item-image',
        name: 'mock-item-name',
        price: 10
      },
      {
        description: 'mock2-item-description',
        id: 'mock2-item-id',
        image: 'mock2-item-image',
        name: 'mock2-item-name',
        price: 20
      }
    ])
  })
  afterEach(() => {
    apiMockAdapter.reset()
  })
  it('should add an item to the order', async () => {
    apiMockAdapter.onPut('/order/mock-id').reply(200)

    render(<RestaurantTable />)

    const selectItemInput = screen.getByRole('combobox')
    await user.click(selectItemInput)
    await user.selectOptions(selectItemInput, screen.getByRole('option', { name: /mock2-item-name/i }))

    const quantityInput = screen.getByRole('spinbutton')
    await user.type(quantityInput, '2')

    apiMockAdapter.onPost('/order').reply(200, {
      id: 'mock-id',
      items: [
        {
          item: {
            description: 'mock-item-description',
            id: 'mock-item-id',
            image: 'mock-item-image',
            name: 'mock-item-name',
            price: 10
          },
          id: 'mock-items-id',
          quantity: 2
        },
        {
          item: {
            description: 'mock2-item-description',
            id: 'mock2-item-id',
            image: 'mock2-item-image',
            name: 'mock2-item-name',
            price: 20
          },
          id: 'mock2-items-id',
          quantity: 2
        }
      ],
      status: 'mock-status',
      tableId: 'mock-tableId',
      tableName: 'mock-tableName',
      userId: 'mock-userId',
      userName: 'mock-userName',
      restaurantName: 'mock-restaurantName'
    })

    expect(screen.queryByRole('row', { name: /mock2-item-name/i })).not.toBeInTheDocument()

    await user.click(
      screen.getByRole('button', {
        name: /adicionar ao pedido/i
      })
    )

    await waitFor(() => {
      expect(screen.getByRole('row', { name: /mock2-item-name/i })).toBeInTheDocument()
    })
  })
  it('should remove an item to the order items', async () => {
    apiMockAdapter.onDelete('/order/mock-items-id').reply(200)

    render(<RestaurantTable />)

    expect(await screen.findByRole('row', { name: /mock-item-name/i })).toBeInTheDocument()

    apiMockAdapter.onPost('/order').reply(200, {
      id: 'mock-id',
      items: [],
      status: 'mock-status',
      tableId: 'mock-tableId',
      tableName: 'mock-tableName',
      userId: 'mock-userId',
      userName: 'mock-userName',
      restaurantName: 'mock-restaurantName'
    })

    await user.click(
      screen.getByRole('button', {
        name: /remover item do pedido/i
      })
    )

    await waitFor(() => {
      expect(screen.queryByRole('row', { name: /mock-item-name/i })).not.toBeInTheDocument()
    })
  })
})
