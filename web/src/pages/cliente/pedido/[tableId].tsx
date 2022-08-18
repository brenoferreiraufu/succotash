import React, { useCallback, useEffect, useState } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import axios from 'axios'
import {
  Container,
  Flex,
  Button,
  Text,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  TableCaption,
  useDisclosure,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  useToast
} from '@chakra-ui/react'
import { payOrderRequest, PostOrderResponse } from 'services/order'
import { moneyFormat } from 'utils/moneyFormat'
import { getTableOrderRequest } from 'services/table'

const ClientOrder: NextPage = () => {
  const [order, setOrder] = useState<PostOrderResponse>()
  const [total, setTotal] = useState<number>()

  const router = useRouter()
  const { isOpen, onOpen, onClose } = useDisclosure()
  const toast = useToast()

  const { tableId } = router.query

  const getTableOrder = useCallback(async () => {
    try {
      if (tableId) {
        const { data } = await getTableOrderRequest({ tableId: tableId as string })
        const totalAmount = data.items.reduce((acc, { item, quantity }) => {
          acc += item.price * quantity
          return acc
        }, 0)

        setTotal(totalAmount)
        setOrder(data)
      }
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error?.response?.status === 404) {
        toast({
          title: 'Atendimento não foi iniciado!',
          status: 'error'
        })
      } else {
        toast({
          title: 'Falha ao carregar detalhes do pedido',
          status: 'error',
          isClosable: true
        })
      }
    }
  }, [tableId, toast])

  useEffect(() => {
    getTableOrder()
  }, [getTableOrder])

  const handleClickPayOrder = async () => {
    if (order) {
      await payOrderRequest({ orderId: order?.id })
      onOpen()
    }
  }

  const handleCloseModal = () => {
    onClose()
    router.back()
  }

  return (
    <>
      <Flex direction="column" height="100%" alignItems="center" justifyContent="center" p={3}>
        <Container centerContent p={3}>
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
                </Tr>
              </Thead>
              <Tbody>
                {order?.items.map(({ item, quantity }, index) => (
                  <Tr key={`${index} - ${item.id}`}>
                    <Td>{item.name}</Td>
                    <Td isNumeric>{quantity}</Td>
                    <Td>{moneyFormat(item.price)}</Td>
                  </Tr>
                ))}
              </Tbody>
            </Table>
          </TableContainer>
          <Flex justifyContent="space-between" width="full" px={10}>
            <Text>Total:</Text>
            <Text fontSize="lg" fontWeight="bold">
              {total !== undefined && moneyFormat(total)}
            </Text>
          </Flex>
          <Button colorScheme="blue" onClick={handleClickPayOrder} mt={5} disabled={!order}>
            Pagar Conta
          </Button>
        </Container>
      </Flex>

      <Modal isOpen={isOpen} onClose={handleCloseModal} isCentered size="sm">
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Pagamento</ModalHeader>
          <ModalCloseButton />
          <ModalBody>Recebemos a confirmação do seu pagamento!</ModalBody>
          <ModalFooter>
            <Button colorScheme="blue" mr={3} onClick={handleCloseModal}>
              Fechar
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

export default ClientOrder
