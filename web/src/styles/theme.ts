import { extendTheme } from '@chakra-ui/react'

const theme = extendTheme({
  fonts: {
    heading: "'Open Sans', sans-serif",
    body: "'Raleway', sans-serif"
  },
  styles: {
    global: {
      'html, body, #__next': {
        height: '100%'
      }
    }
  }
})

export default theme
