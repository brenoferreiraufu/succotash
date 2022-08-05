import { Flex } from '@chakra-ui/react'
import type { NextPage } from 'next'

const Home: NextPage = () => {
  return (
    <Flex height="100vh" alignItems="center" justifyContent="center">
      <Flex direction="column" p={12} rounded={6}>
        succotash...
      </Flex>
    </Flex>
  )
}

export default Home
