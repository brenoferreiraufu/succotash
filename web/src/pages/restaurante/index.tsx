import React, { useState, useEffect } from 'react'
import type { GetServerSideProps, NextPage } from 'next'
import { useRouter } from 'next/router'
import { Flex, Button, Select, Box, useToast } from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'
import { parseCookies } from 'nookies'
import { getTablesRequest } from 'services/table'

const Restaurant: NextPage = () => {
  const [table, setTable] = useState('')
  const [tables, setTables] = useState<{ id: string; name: string }[]>([])
  const router = useRouter()
  const toast = useToast()

  const handleSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setTable(e.target.value)
  }

  useEffect(() => {
    const fetchTables = async () => {
      try {
        const { data } = await getTablesRequest()
        setTables(data)
      } catch {
        toast({
          title: 'Falha ao buscar as mesas do restaurante',
          status: 'error',
          isClosable: true
        })
      }
    }

    fetchTables()
  }, [toast])

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
        <Box as="img" src="/images/logo_transparent.png" alt="Foto do Restaurante Xpto" width={250} my={5} />
        {/* <Text fontSize="xl" fontWeight="bold">
          Restaurante Xpto
        </Text> */}
        <Select placeholder="Selecione a mesa" onChange={handleSelect} maxWidth="350px" mt={5}>
          {tables.map((item) => (
            <option key={item.id} value={item.id}>
              {item.name}
            </option>
          ))}
        </Select>
        <Button colorScheme="blue" onClick={() => router.push(`/restaurante/${table}`)} mt={5} isDisabled={!table}>
          Selecionar mesa
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
