import { ReactElement, ReactNode } from 'react'
import { render as rtlRender, RenderOptions } from '@testing-library/react'
import { AuthProvider } from 'contexts/AuthContext'

function customRender(ui: ReactElement, options?: RenderOptions) {
  const Wrapper = ({ children }: { children: ReactNode }) => {
    return <AuthProvider>{children}</AuthProvider>
  }
  return rtlRender(ui, { wrapper: Wrapper, ...options })
}

export * from '@testing-library/react'

export { customRender as render }
