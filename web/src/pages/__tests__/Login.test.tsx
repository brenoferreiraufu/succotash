import { render, screen } from 'test/test-utils'
import userEvent from '@testing-library/user-event'
import Login from '..'

const mockedSignIn = jest.fn()

jest.mock('contexts/AuthContext', () => ({
  ...jest.requireActual('contexts/AuthContext'),
  useAuthContext: () => ({
    signIn: mockedSignIn
  })
}))

const userCredentials = {
  username: 'user',
  password: 'mock-password'
}

describe('<Login />', () => {
  it('should switch the password input to input type text when user click the show button', async () => {
    render(<Login />)
    const user = userEvent.setup()

    const usernameInput = screen.getByRole('textbox', {
      name: /usuário/i
    })
    const passwordInput = screen.getByLabelText(/senha\*/i)
    const showPasswordButton = screen.getByRole('button', {
      name: /mostrar/i
    })

    await user.type(usernameInput, userCredentials.username)
    await user.type(passwordInput, userCredentials.password)

    expect(usernameInput).toHaveDisplayValue(userCredentials.username)
    expect(passwordInput).toHaveAttribute('type', 'password')

    await user.click(showPasswordButton)

    expect(passwordInput).toHaveAttribute('type', 'text')
  })
  it('should call signIn function', async () => {
    render(<Login />)
    const user = userEvent.setup()

    const usernameInput = screen.getByRole('textbox', {
      name: /usuário/i
    })
    const passwordInput = screen.getByLabelText(/senha\*/i)

    await user.type(usernameInput, userCredentials.username)
    await user.type(passwordInput, userCredentials.password)

    expect(usernameInput).toHaveDisplayValue(userCredentials.username)
    expect(passwordInput).toHaveAttribute('type', 'password')

    await user.click(screen.getByRole('button', { name: /entrar/i }))

    expect(mockedSignIn).toHaveBeenCalled()
  })
})
