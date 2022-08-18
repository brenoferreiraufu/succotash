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
  TableCaption,
  useToast
} from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'
import { AddIcon, DeleteIcon } from '@chakra-ui/icons'
import { parseCookies } from 'nookies'
import { createOrderRequest, editOrderRequest, PostOrderResponse, removeOrderItemRequest } from 'services/order'
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
  const toast = useToast()

  const { tableId } = router.query

  const getTableOrder = useCallback(async () => {
    try {
      const { data } = await createOrderRequest({ tableId: tableId as string })
      setOrder(data)
    } catch {
      toast({
        title: 'Falha ao buscar detalhes do pedido',
        status: 'error',
        isClosable: true
      })
    }
  }, [tableId, toast])

  const getItems = useCallback(async () => {
    try {
      const { data } = await getItemsRequest()
      setItems(data)
    } catch {
      toast({
        title: 'Falha ao buscar itens do menu',
        status: 'error',
        isClosable: true
      })
    }
  }, [toast])

  useEffect(() => {
    getTableOrder()
    getItems()
  }, [getItems, getTableOrder])

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
        ...order.items.map(({ quantity, id }) => ({ orderItemId: id, quantity })),
        { item: { id: orderItem.idItem }, quantity: Number(orderItem.quantity) }
      ]
      try {
        await editOrderRequest({ orderId: order.id, items })
      } catch (error) {
        toast({
          title: 'Falha ao atualizar pedido',
          status: 'error',
          isClosable: true
        })
      }
      await getTableOrder()
    }
  }

  const handleClickRemoveItem = async (orderItemId: string) => {
    try {
      await removeOrderItemRequest({ orderItemId })
    } catch {
      toast({
        title: 'Falha ao remover item do pedido',
        status: 'error',
        isClosable: true
      })
    }
    await getTableOrder()
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
          Restaurante {order?.restaurantName}
        </Text>
        <Text fontSize="lg" fontWeight="bold">
          {order?.tableName}
        </Text>
        <Text fontSize="sm" fontWeight="bold">
          Garçom: {order?.userName}
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
              {order?.items.map(({ item, quantity, id }, index) => (
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
                      onClick={() => handleClickRemoveItem(id)}
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
