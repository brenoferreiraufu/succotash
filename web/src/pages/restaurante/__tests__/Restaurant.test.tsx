import { mockedToastFn } from 'test/mocks/useToast'

import { render, screen, waitFor } from 'test/test-utils'
import userEvent from '@testing-library/user-event'
import Restaurant from '..'
import { apiMockAdapter } from 'test/mocks/api'

const mockedBackFn = jest.fn()

jest.mock('next/router', () => ({
  ...jest.requireActual('next/router'),
  useRouter: () => ({
    back: mockedBackFn
  })
}))

describe('<Restaurant />', () => {
  const user = userEvent.setup()
  beforeEach(() => {
    apiMockAdapter.onGet('/table/').reply(200, [{ id: '0', name: 'Mesa 0' }])
  })
  afterEach(() => {
    apiMockAdapter.reset()
    jest.clearAllMocks()
  })
  it('should disable the button if there is no table selected', async () => {
    render(<Restaurant />)

    const selectInput = screen.getByRole('combobox')

    expect(selectInput).toHaveDisplayValue('Selecione a mesa')

    expect(screen.getByRole('button', { name: /selecionar mesa/i })).toBeDisabled()
  })
  it('should enable the button if a table is selected', async () => {
    render(<Restaurant />)

    const selectInput = screen.getByRole('combobox')

    expect(selectInput).toHaveDisplayValue('Selecione a mesa')

    expect(screen.getByRole('button', { name: /selecionar/i })).toBeDisabled()
    await user.click(selectInput)
    expect(await screen.findByRole('option', { name: /mesa 0/i })).toBeVisible()
    await user.selectOptions(selectInput, screen.getByRole('option', { name: /mesa 0/i }))

    expect(selectInput).toHaveDisplayValue('Mesa 0')
    expect(screen.getByRole('button', { name: /selecionar/i })).not.toBeDisabled()
  })
  it('should call a toast if the tables request goes wrong', async () => {
    apiMockAdapter.onGet('/table/').reply(400)

    render(<Restaurant />)

    await waitFor(() => {
      expect(mockedToastFn).toHaveBeenCalledWith({
        title: 'Falha ao buscar as mesas do restaurante',
        status: 'error',
        isClosable: true
      })
    })
  })
})
