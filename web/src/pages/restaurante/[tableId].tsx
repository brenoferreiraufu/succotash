import React, { useCallback, useEffect, useState } from 'react'
import type { GetServerSideProps, NextPage } from 'next'
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
import { parseCookies } from 'nookies'
import { createOrderRequest, editOrderRequest, PostOrderResponse } from 'services/order'
import { getItemsRequest, GetItemsResponse } from 'services/item'
import { moneyFormat } from 'utils/moneyFormat'

type OrderItem = {
  idItem: string
  quantity: string
}

const Restaurant: NextPage = () => {
  const [items, setItems] = useState<GetItemsResponse>()
  const [order, setOrder] = useState<PostOrderResponse>()
  const [orderItem, setOrderItem] = useState<OrderItem>({
    idItem: '',
    quantity: ''
  })

  const router = useRouter()

  const { tableId } = router.query

  const getTableOrder = useCallback(async () => {
    const { data } = await createOrderRequest({ tableId: tableId as string })
    setOrder(data)
  }, [tableId])

  const getItems = async () => {
    const { data } = await getItemsRequest()
    setItems(data)
  }

  useEffect(() => {
    getTableOrder()
    getItems()
  }, [getTableOrder])

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

  const handleAddOrderItem = async () => {
    if (order) {
      const items = [
        ...order.items.map(({ item, quantity }) => ({ item: { id: item.id }, quantity })),
        { item: { id: orderItem.idItem }, quantity: Number(orderItem.quantity) }
      ]
      await editOrderRequest({ orderId: order.id, items })
      // Melhoria retornar o pedido atualizado do PUT
      // Paliativo \/
      await getTableOrder()
    }
  }

  const handleClickRemoveItem = async (index: number) => {
    if (order) {
      const items = [...order.items.map(({ item, quantity }) => ({ item: { id: item.id }, quantity }))]
      items.splice(index, 1)

      await editOrderRequest({ orderId: order.id, items })
      // Melhoria retornar o pedido atualizado do PUT
      // Paliativo \/
      await getTableOrder()
    }
  }

  return (
    <>
      <Header backButton />
      <Flex
        direction="column"
        height={`calc(100% - ${headerHeight}px)`}
        alignItems="center"
        justifyContent="center"
        p={3}
      >
        <Text fontSize="xl" fontWeight="bold">
          Restaurante Tropeiro
        </Text>
        <Text fontSize="lg" fontWeight="bold">
          {order?.tableId}
        </Text>
        <Text fontSize="sm" fontWeight="bold">
          Garçom: {order?.userId}
        </Text>
        <TableContainer my={5} overflowY="scroll" maxHeight={300}>
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
              {order?.items.map(({ item, quantity }, index) => (
                <Tr key={`${index} - ${item.id}`}>
                  <Td>{item.name}</Td>
                  <Td isNumeric>{quantity}</Td>
                  <Td>{moneyFormat(item.price)}</Td>
                  <Td>
                    <IconButton
                      size="sm"
                      aria-label="Remover item do pedido"
                      icon={<DeleteIcon />}
                      variant="unstyled"
                      color="red.400"
                      onClick={() => handleClickRemoveItem(index)}
                    />
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </TableContainer>
        <Flex columnGap={2}>
          <Select placeholder="Selecione" onChange={handleSelect} maxWidth="350px" value={orderItem.idItem}>
            {items?.map(({ id, name }) => (
              <option key={id} value={id}>
                {name}
              </option>
            ))}
          </Select>
          <Input
            value={orderItem.quantity}
            type="number"
            placeholder="Quantidade"
            onChange={handleInputChange}
            width={220}
          />
          <IconButton
            aria-label="Adicionar ao pedido"
            icon={<AddIcon />}
            onClick={handleAddOrderItem}
            disabled={!orderItem.idItem || !orderItem.quantity}
          />
        </Flex>
        <Button colorScheme="blue" onClick={router.back} mt={5}>
          Voltar
        </Button>
      </Flex>
    </>
  )
}

export const getServerSideProps: GetServerSideProps = async (ctx) => {
  const { ['nextauth.token']: token } = parseCookies(ctx)

  if (!token) {
    return {
      redirect: {
        destination: '/',
        permanent: false
      }
    }
  }

  return {
    props: {}
  }
}

export default Restaurant
