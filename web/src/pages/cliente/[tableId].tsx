import React, { useCallback, useEffect, useState } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import { Flex, Button, Text, Box, Skeleton } from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'
import { getTableRequest, GetTableResponse } from 'services/table'

const Restaurant: NextPage = () => {
  const [tableInfo, setTableIndo] = useState<GetTableResponse>()
  const [loadingTableInfo, setLoadingTableInfo] = useState(true)
  const router = useRouter()

  const { tableId } = router.query

  const getTableInfo = useCallback(async () => {
    setLoadingTableInfo(true)
    const { data } = await getTableRequest({ tableId: tableId as string })
    setTableIndo(data)
    setLoadingTableInfo(false)
  }, [tableId])

  useEffect(() => {
    if (tableId) {
      getTableInfo()
    }
  }, [getTableInfo, tableId])

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
        <Box
          as="img"
          src="/images/logo_transparent.png"
          alt={`Foto do ${tableInfo?.restaurant.name}`}
          width={250}
          my={5}
        />
        <Text fontSize="xl" fontWeight="bold">
          {tableInfo?.restaurant.name}
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

export default Restaurant
