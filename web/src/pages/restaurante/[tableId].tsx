import React, { useEffect, useState } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import {
  Flex,
  Button,
  Select,
  Text,
  IconButton,
  Input,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  TableCaption
} from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'
import { AddIcon, DeleteIcon } from '@chakra-ui/icons'

type OrderItem = {
  idItem: string
  quantity: string
}

const Restaurant: NextPage = () => {
  const [orderItem, setOrderItem] = useState<OrderItem>({
    idItem: '',
    quantity: ''
  })
  const router = useRouter()

  const { tableId } = router.query

  useEffect(() => {
    // Api call
    console.log(tableId)
  }, [tableId])

  const handleSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setOrderItem((oldOrderItem) => ({
      ...oldOrderItem,
      idItem: e.target.value
    }))
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOrderItem((oldOrderItem) => ({
      ...oldOrderItem,
      quantity: e.target.value
    }))
  }

  const handleAddOrderItem = () => {
    if (!orderItem.idItem || !orderItem.quantity) {
      // TODO alert
    } else {
      // Api call invalidate cache
    }
  }

  const handleClickEndService = () => {
    // api call
    console.log('Atendimento finalizado')
    router.back()
  }

  const handleClickRemoveItem = (id: string) => {
    // api call
    console.log(`Item ${id} removido`)
  }

  return (
    <>
      <Header />
      <Flex
        direction="column"
        height={`calc(100% - ${headerHeight}px)`}
        alignItems="center"
        justifyContent="center"
        p={3}
      >
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
                <Th />
              </Tr>
            </Thead>
            <Tbody>
              <Tr>
                <Td>Água</Td>
                <Td isNumeric>2</Td>
                <Td>R$ 12,00</Td>
                <Td>
                  <IconButton
                    size="sm"
                    aria-label="Remover item do pedido"
                    icon={<DeleteIcon />}
                    variant="unstyled"
                    color="red.400"
                    onClick={() => handleClickRemoveItem('1')}
                  />
                </Td>
              </Tr>
              <Tr>
                <Td>Água</Td>
                <Td isNumeric>2</Td>
                <Td>R$ 12,00</Td>
                <Td>
                  <IconButton
                    size="sm"
                    aria-label="Remover item do pedido"
                    icon={<DeleteIcon />}
                    variant="unstyled"
                    color="red.400"
                    onClick={() => handleClickRemoveItem('2')}
                  />
                </Td>
              </Tr>
              <Tr>
                <Td>Água</Td>
                <Td isNumeric>2</Td>
                <Td>R$ 12,00</Td>
                <Td>
                  <IconButton
                    size="sm"
                    aria-label="Remover item do pedido"
                    icon={<DeleteIcon />}
                    variant="unstyled"
                    color="red.400"
                    onClick={() => handleClickRemoveItem('3')}
                  />
                </Td>
              </Tr>
            </Tbody>
          </Table>
        </TableContainer>
        <Flex columnGap={2}>
          <Select placeholder="Selecione" onChange={handleSelect} maxWidth="350px" value={orderItem.idItem}>
            <option value="1">Item 1</option>
            <option value="2">Item 2</option>
            <option value="3">Item 3</option>
          </Select>
          <Input value={orderItem.quantity} placeholder="Quantidade" onChange={handleInputChange} width={220} />
          <IconButton aria-label="Adicionar ao pedido" icon={<AddIcon />} onClick={handleAddOrderItem} />
        </Flex>
        <Button colorScheme="blue" onClick={handleClickEndService} mt={5}>
          Finalizar Atendimento
        </Button>
      </Flex>
    </>
  )
}

export default Restaurant
