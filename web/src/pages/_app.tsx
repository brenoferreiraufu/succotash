import type { AppProps } from 'next/app'
import { ChakraProvider } from '@chakra-ui/react'
import Head from 'next/head'
import theme from 'styles/theme'

import '@fontsource/raleway'
import '@fontsource/open-sans'
import { AuthProvider } from 'contexts/AuthContext'

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <AuthProvider>
      <ChakraProvider resetCSS theme={theme}>
        <Head>
          <title>Succotash</title>
          <meta name="description" content="A melhor forma de pagar sua conta em bares e restaurantes" />
          <link rel="icon" href="/favicon.png" />
        </Head>
        <Component {...pageProps} />
      </ChakraProvider>
    </AuthProvider>
  )
}

export default MyApp
