import { Box, Text, Icon, IconButton } from '@chakra-ui/react'
import { FiLogOut } from 'react-icons/fi'
import { useAuthContext } from 'contexts/AuthContext'
import { ChevronLeftIcon } from '@chakra-ui/icons'
import { useRouter } from 'next/router'

export const headerHeight = 75

type Props = {
  backButton?: boolean
  logoutButton?: boolean
}

const Header = ({ backButton, logoutButton = true }: Props) => {
  const { logout } = useAuthContext()
  const router = useRouter()

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
      {backButton && (
        <IconButton
          aria-label="Voltar página"
          icon={<ChevronLeftIcon />}
          onClick={router.back}
          position="absolute"
          left={4}
          top={4}
        />
      )}
      {logoutButton && (
        <IconButton
          aria-label="Logout"
          icon={<Icon as={FiLogOut} />}
          position="absolute"
          right={4}
          top={4}
          onClick={logout}
        />
      )}
      <Text align="center" fontSize="2xl" fontWeight="bold" fontStyle="italic">
        Succotash
      </Text>
    </Box>
  )
}

export default Header
