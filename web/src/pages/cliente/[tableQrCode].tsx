import React, { useEffect } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import { Flex, Button, Text, Box } from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'

const Restaurant: NextPage = () => {
  const router = useRouter()

  const { tableQrCode } = router.query

  useEffect(() => {
    console.log('buscar id de mesa e pedido?')
  }, [tableQrCode])

  const tableId = 'xpto'

  return (
    <>
      <Header />
      <Flex
        direction="column"
        height={`calc(100% - ${headerHeight}px)`}
        alignItems="center"
        justifyContent="center"
        p={12}
      >
        <Box as="img" src="/images/logo_transparent.png" alt="Foto do restaurante Xpto" width={250} my={5} />
        <Text fontSize="xl" fontWeight="bold">
          Restaurante Xpto
        </Text>
        <Text fontSize="lg" fontWeight="bold">
          Mesa X
        </Text>
        <Button colorScheme="blue" onClick={() => router.push(`/cliente/pedido/${tableId}`)} mt={5}>
          Entrar
        </Button>
      </Flex>
    </>
  )
}

export default Restaurant
