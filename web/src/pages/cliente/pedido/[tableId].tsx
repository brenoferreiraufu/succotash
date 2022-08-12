import React, { useEffect } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import { Flex, Button, Text, Table, Thead, Tbody, Tr, Th, Td, TableContainer, TableCaption } from '@chakra-ui/react'

const Restaurant: NextPage = () => {
  const router = useRouter()

  const { tableId } = router.query

  console.log(process.env.NEXT_PUBLIC_API_URL)

  useEffect(() => {
    // Api call
    console.log(tableId)
  }, [tableId])

  const handleClickPayOrder = () => {
    console.log('Pagar conta...')
    router.back()
  }

  return (
    <>
      <Flex direction="column" height="100%" alignItems="center" justifyContent="center" p={3}>
        <Text fontSize="xl" fontWeight="bold">
          Restaurante Xpto
        </Text>
        <Text fontSize="lg" fontWeight="bold">
          Mesa X
        </Text>
        <Text fontSize="sm" fontWeight="bold">
          Garçom: Fulano
        </Text>
        <TableContainer mt={10} mb={5}>
          <Table variant="striped" colorScheme="blackAlpha" size="sm">
            <TableCaption>Detalhamento do pedido</TableCaption>
            <Thead>
              <Tr>
                <Th>Item</Th>
                <Th isNumeric>Quantidade</Th>
                <Th>Valor</Th>
              </Tr>
            </Thead>
            <Tbody>
              <Tr>
                <Td>Água</Td>
                <Td isNumeric>2</Td>
                <Td>R$ 12,00</Td>
              </Tr>
              <Tr>
                <Td>Água</Td>
                <Td isNumeric>2</Td>
                <Td>R$ 12,00</Td>
              </Tr>
              <Tr>
                <Td>Água</Td>
                <Td isNumeric>2</Td>
                <Td>R$ 12,00</Td>
              </Tr>
            </Tbody>
          </Table>
        </TableContainer>
        <Flex justifyContent="space-between" width="full" px={10}>
          <Text>Total:</Text>
          <Text fontSize="lg" fontWeight="bold">
            R$ 2.000,00
          </Text>
        </Flex>
        <Button colorScheme="blue" onClick={handleClickPayOrder} mt={5}>
          Pagar Conta
        </Button>
      </Flex>
    </>
  )
}

export default Restaurant
