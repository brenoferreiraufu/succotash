import React, { useCallback, useEffect, useState } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import { Flex, Button, Text, Box, Skeleton, useToast } from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'
import { getTableRequest, GetTableResponse } from 'services/table'

const ClientTable: NextPage = () => {
  const [tableInfo, setTableInfo] = useState<GetTableResponse>()
  const [loadingTableInfo, setLoadingTableInfo] = useState(true)

  const router = useRouter()
  const toast = useToast()

  const { tableId } = router.query

  const getTableInfo = useCallback(async () => {
    setLoadingTableInfo(true)
    try {
      const { data } = await getTableRequest({ tableId: tableId as string })
      setTableInfo(data)
    } catch (error) {
      toast({
        title: 'Falha ao carregar as informações da mesa',
        status: 'error',
        isClosable: true
      })
    } finally {
      setLoadingTableInfo(false)
    }
  }, [tableId, toast])

  useEffect(() => {
    if (tableId) {
      getTableInfo()
    }
  }, [getTableInfo, tableId])

  return (
    <>
      <Header logoutButton={false} />
      <Flex
        direction="column"
        height={`calc(100% - ${headerHeight}px)`}
        alignItems="center"
        justifyContent="center"
        p={12}
      >
        <Box
          as="img"
          src="/images/logo_transparent.png"
          alt={`Foto do ${tableInfo?.restaurant.name}`}
          width={250}
          my={5}
        />
        <Text fontSize="xl" fontWeight="bold">
          Restaurante {tableInfo?.restaurant.name}
        </Text>
        {loadingTableInfo ? (
          <Skeleton height="32px" width={100} />
        ) : (
          <Text fontSize="lg" fontWeight="bold">
            {tableInfo?.name}
          </Text>
        )}
        <Button colorScheme="blue" onClick={() => router.push(`/cliente/pedido/${tableId}`)} mt={5}>
          Entrar
        </Button>
      </Flex>
    </>
  )
}

export default ClientTable
