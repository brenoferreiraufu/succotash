import { render, screen } from 'test/test-utils'
import { mockedToastFn } from 'test/mocks/useToast'
import userEvent from '@testing-library/user-event'
import Cadastro from '../cadastro'
import { apiMockAdapter } from 'services/api'

const mockedBackFn = jest.fn()

jest.mock('next/router', () => ({
  ...jest.requireActual('next/router'),
  useRouter: () => ({
    back: mockedBackFn,
    push: jest.fn()
  })
}))

const userCredentials = {
  fullName: 'fullName',
  username: 'user',
  password: 'mock-password'
}

describe('<Cadastro />', () => {
  const setup = () => {
    const user = userEvent.setup()

    const passwordInput = screen.getByPlaceholderText('Digite sua senha')
    const showPasswordButton = screen.getByTestId('show-password')
    const confirmPasswordInput = screen.getByPlaceholderText('Confirme sua senha')
    const showConfirmPasswordButton = screen.getByTestId('show-confirm-password')
    const fullNameInput = screen.getByRole('textbox', {
      name: /nome completo/i
    })
    const userInput = screen.getByRole('textbox', {
      name: /usuário/i
    })
    const registerButton = screen.getByRole('button', {
      name: /cadastrar/i
    })

    return {
      user,
      passwordInput,
      showPasswordButton,
      confirmPasswordInput,
      showConfirmPasswordButton,
      fullNameInput,
      userInput,
      registerButton
    }
  }
  beforeAll(() => {
    apiMockAdapter.onGet('/user').reply(200)
  })
  it('should switch the password and confirm password input to input type text when user click the show button', async () => {
    render(<Cadastro />)
    const { user, passwordInput, showPasswordButton, confirmPasswordInput, showConfirmPasswordButton } = setup()

    expect(passwordInput).toHaveAttribute('type', 'password')
    expect(confirmPasswordInput).toHaveAttribute('type', 'password')
    await user.click(showPasswordButton)
    await user.click(showConfirmPasswordButton)
    expect(passwordInput).toHaveAttribute('type', 'text')
    expect(confirmPasswordInput).toHaveAttribute('type', 'text')
  })
  it('should register a user', async () => {
    render(<Cadastro />)
    const { user, passwordInput, fullNameInput, confirmPasswordInput, userInput, registerButton } = setup()

    await user.type(fullNameInput, userCredentials.fullName)
    await user.type(userInput, userCredentials.username)
    await user.type(passwordInput, userCredentials.password)
    await user.type(confirmPasswordInput, userCredentials.password)

    await user.click(registerButton)

    expect(mockedToastFn).toHaveBeenCalled()
  })
})
