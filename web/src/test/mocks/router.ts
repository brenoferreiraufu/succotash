const mockedBackFn = jest.fn()
const mockedPushFn = jest.fn()

jest.mock('next/router', () => ({
  ...jest.requireActual('next/router'),
  useRouter: () => ({
    back: mockedBackFn,
    push: mockedPushFn
  })
}))

export { mockedBackFn, mockedPushFn }
