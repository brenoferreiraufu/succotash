const mockedToastFn = jest.fn()

jest.mock('@chakra-ui/react', () => ({
  ...jest.requireActual('@chakra-ui/react'),
  useToast: () => mockedToastFn
}))

export { mockedToastFn }
