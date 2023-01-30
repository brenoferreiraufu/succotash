import { render, screen } from 'test/test-utils'
import userEvent from '@testing-library/user-event'
import Header from 'components/Header'

jest.mock('next/router', () => ({
  useRouter: jest.fn().mockReturnValue({ back: jest.fn() })
}))

const mockedLogout = jest.fn()

jest.mock('contexts/AuthContext', () => ({
  ...jest.requireActual('contexts/AuthContext'),
  useAuthContext: () => ({
    logout: mockedLogout
  })
}))

describe('<Header />', () => {
  it('should display the logout button by default', () => {
    render(<Header />)
    const logoutButton = screen.getByLabelText('Logout')
    expect(logoutButton).toBeInTheDocument()
  })

  it('should not display the logout button if logoutButton is set to false', () => {
    render(<Header logoutButton={false} />)
    const logoutButton = screen.queryByLabelText('Logout')
    expect(logoutButton).not.toBeInTheDocument()
  })

  it('should call logout when logout button is clicked', async () => {
    const user = userEvent.setup()
    render(<Header />)
    const logoutButton = screen.getByLabelText('Logout')
    await user.click(logoutButton)
    expect(mockedLogout).toHaveBeenCalled()
  })

  it('should display the back button if backButton is set to true', () => {
    render(<Header backButton />)
    const backButton = screen.getByLabelText('Voltar página')
    expect(backButton).toBeInTheDocument()
  })

  it('should not display the back button if backButton is set to false', () => {
    render(<Header />)
    const backButton = screen.queryByLabelText('Voltar página')
    expect(backButton).not.toBeInTheDocument()
  })
})
