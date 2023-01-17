import React, { useState } from 'react'
import { useRouter } from 'next/router'
import {
  Box,
  Flex,
  Button,
  Input,
  InputGroup,
  InputRightElement,
  FormControl,
  FormLabel,
  FormErrorMessage,
  IconButton,
  useToast
} from '@chakra-ui/react'
import type { NextPage } from 'next'
import { ChevronLeftIcon } from '@chakra-ui/icons'
import { registerUserRequest } from 'services/user'

type RegisterForm = {
  fullName: string
  username: string
  password: string
  confirmPassword: string
}

const Register: NextPage = () => {
  const [registerFormValues, setRegisterFormValues] = useState<RegisterForm>({
    fullName: '',
    username: '',
    password: '',
    confirmPassword: ''
  })
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)

  const router = useRouter()
  const toast = useToast()

  const passwordMatch = registerFormValues.password === registerFormValues.confirmPassword

  const handleClickPasswordButton = () => setShowPassword(!showPassword)
  const handleClickConfirmPasswordButton = () => setShowConfirmPassword(!showConfirmPassword)

  const handleInputChange = (field: keyof RegisterForm) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setRegisterFormValues((oldFormValues) => ({
      ...oldFormValues,
      [field]: e.target.value
    }))
  }

  const handleSubmitForm = async (e: React.FormEvent) => {
    e.preventDefault()
    if (passwordMatch) {
      const { fullName, password, username } = registerFormValues
      try {
        await registerUserRequest({ fullName, password, username })
        toast({
          title: 'Usuário cadastrado com sucesso',
          status: 'success',
          isClosable: true
        })
        router.push('/')
      } catch (error) {
        toast({
          title: 'Não foi possível criar o usuário, tente novamente',
          status: 'error',
          isClosable: true
        })
      }
    } else {
      toast({
        title: 'Senhas diferentes',
        status: 'error',
        isClosable: true
      })
    }
  }

  return (
    <Flex height="100vh" justifyContent="center" position="relative">
      <IconButton
        aria-label="Voltar página"
        icon={<ChevronLeftIcon />}
        onClick={router.back}
        position="absolute"
        left={8}
        top={5}
      />
      <Flex direction="column" px={8} py={5} rounded={6} alignItems="center" justifyContent="center" width="full">
        <img src="/images/logo_transparent.png" alt="Logo Succotash" width={250} />
        <Box as="form" onSubmit={handleSubmitForm} mt={-5}>
          <FormControl isRequired>
            <FormLabel>Nome completo</FormLabel>
            <Input
              value={registerFormValues.fullName}
              placeholder="Digite seu nome completo"
              onChange={handleInputChange('fullName')}
            />
          </FormControl>
          <FormControl mt={2} isRequired>
            <FormLabel>Usuário</FormLabel>
            <Input
              value={registerFormValues.username}
              placeholder="Digite seu usuário"
              onChange={handleInputChange('username')}
            />
          </FormControl>
          <FormControl mt={2} isRequired>
            <FormLabel>Senha</FormLabel>
            <InputGroup size="md">
              <Input
                pr="4.5rem"
                type={showPassword ? 'text' : 'password'}
                placeholder="Digite sua senha"
                value={registerFormValues.password}
                onChange={handleInputChange('password')}
              />
              <InputRightElement width="6.5rem">
                <Button h="1.75rem" size="sm" onClick={handleClickPasswordButton} data-testid="show-password">
                  {showPassword ? 'Esconder' : 'Mostrar'}
                </Button>
              </InputRightElement>
            </InputGroup>
          </FormControl>
          <FormControl mt={2} isRequired isInvalid={!passwordMatch}>
            <FormLabel>Confirmar senha</FormLabel>
            <InputGroup size="md">
              <Input
                pr="4.5rem"
                type={showConfirmPassword ? 'text' : 'password'}
                placeholder="Confirme sua senha"
                value={registerFormValues.confirmPassword}
                onChange={handleInputChange('confirmPassword')}
              />
              <InputRightElement width="6.5rem">
                <Button
                  h="1.75rem"
                  size="sm"
                  onClick={handleClickConfirmPasswordButton}
                  data-testid="show-confirm-password"
                >
                  {showConfirmPassword ? 'Esconder' : 'Mostrar'}
                </Button>
              </InputRightElement>
            </InputGroup>
            <FormErrorMessage>Senhas diferentes</FormErrorMessage>
          </FormControl>
          <Button type="submit" colorScheme="blue" mt={5} width="full">
            Cadastrar
          </Button>
        </Box>
      </Flex>
    </Flex>
  )
}

export default Register
