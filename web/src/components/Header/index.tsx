import React from 'react'
import { Box, Text, Icon, IconButton } from '@chakra-ui/react'
import { FiLogOut } from 'react-icons/fi'

import type { NextPage } from 'next'

export const headerHeight = 75

const Header: NextPage = () => {
  return (
    <Box
      as="header"
      width="100%"
      height={`${headerHeight}px`}
      display="flex"
      alignItems="center"
      justifyContent="center"
      position="relative"
    >
      <IconButton aria-label="Logout" icon={<Icon as={FiLogOut} />} position="absolute" right={4} top={4} />
      <Text align="center" fontSize="2xl" fontWeight="bold" fontStyle="italic">
        Succotash
      </Text>
    </Box>
  )
}

export default Header
