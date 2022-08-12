import React, { useState } from 'react'
import type { NextPage } from 'next'
import { useRouter } from 'next/router'
import { Flex, Button, Select, Text, Box } from '@chakra-ui/react'
import Header, { headerHeight } from 'components/Header'

const Restaurant: NextPage = () => {
  const [table, setTable] = useState('')
  const router = useRouter()

  const handleSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setTable(e.target.value)
  }

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
        <Select placeholder="Selecione a mesa" onChange={handleSelect} maxWidth="350px">
          <option value="mesa1">Mesa 1</option>
          <option value="mesa2">Mesa 2</option>
          <option value="mesa3">Mesa 3</option>
        </Select>
        <Button colorScheme="blue" onClick={() => router.push(`/restaurante/${table}`)} mt={5} disabled={!table}>
          Selecionar mesa
        </Button>
      </Flex>
    </>
  )
}

export default Restaurant
