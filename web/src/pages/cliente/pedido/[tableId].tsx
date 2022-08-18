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
      const { data } = await getTableOrderRequest({ tableId: tableId as string })
      const totalAmount = data.items.reduce((acc, { item, quantity }) => {
        acc += item.price * quantity
        return acc
      }, 0)

      setTotal(totalAmount)
      setOrder(data)
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error?.response?.status === 404) {
        toast({
          title: 'Atendimento não foi iniciado!',
          status: 'error'
        })
      } else {
        toast({
          title: 'No momento não conseguimos recuperar o pedido, tente novamente mais tarde!',
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

  return (
    <>
      <Flex direction="column" height="100%" alignItems="center" justifyContent="center" p={3}>
        <Container centerContent p={3}>
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
              {total && moneyFormat(total)}
            </Text>
          </Flex>
          <Button colorScheme="blue" onClick={handleClickPayOrder} mt={5} disabled={!order}>
            Pagar Conta
          </Button>
        </Container>
      </Flex>

      <Modal isOpen={isOpen} onClose={onClose} isCentered size="sm">
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Pagamento</ModalHeader>
          <ModalCloseButton />
          <ModalBody>Recebemos a confirmação do seu pagamento!</ModalBody>
          <ModalFooter>
            <Button colorScheme="blue" mr={3} onClick={onClose}>
              Fechar
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

export default ClientOrder
