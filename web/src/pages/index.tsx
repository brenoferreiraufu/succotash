import React, { useState, useContext } from 'react'
import NextLink from 'next/link'
import {
  useToast,
  Flex,
  Button,
  Input,
  InputGroup,
  InputRightElement,
  FormControl,
  FormLabel,
  Link
} from '@chakra-ui/react'
import type { NextPage } from 'next'
import { AuthContext } from 'contexts/AuthContext'
import { useRouter } from 'next/router'

type LoginForm = {
  username: string
  password: string
}

const Login: NextPage = () => {
  const [show, setShow] = useState(false)
  const { signIn } = useContext(AuthContext)
  const toast = useToast()
  const router = useRouter()

  const [loginFormValues, setLoginFormValues] = useState<LoginForm>({
    username: '',
    password: ''
  })

  const handleClickPasswordButton = () => setShow(!show)

  const handleInputChange = (field: keyof LoginForm) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setLoginFormValues((oldFormValues) => ({
      ...oldFormValues,
      [field]: e.target.value
    }))
  }

  const handleSubmitForm = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await signIn(loginFormValues)
      router.push('/restaurante')
    } catch (error) {
      toast({
        title: 'Não foi possível se autenticar',
        status: 'error',
        isClosable: true
      })
    }
  }

  return (
    <Flex height="100vh" alignItems="center" justifyContent="center">
      <Flex direction="column" p={12} rounded={6} alignItems="center">
        <img src="/images/logo_transparent.png" alt="Foto de um restaurante" width={250} />
        <form onSubmit={handleSubmitForm}>
          <FormControl isRequired>
            <FormLabel>Usuário</FormLabel>
            <Input placeholder="Digite seu usuário" onChange={handleInputChange('username')} />
          </FormControl>
          <FormControl mt={3} isRequired>
            <FormLabel>Senha</FormLabel>
            <InputGroup size="md">
              <Input
                pr="4.5rem"
                type={show ? 'text' : 'password'}
                placeholder="Digite sua senha"
                onChange={handleInputChange('password')}
              />
              <InputRightElement width="6.5rem">
                <Button h="1.75rem" size="sm" onClick={handleClickPasswordButton}>
                  {show ? 'Esconder' : 'Mostrar'}
                </Button>
              </InputRightElement>
            </InputGroup>
          </FormControl>
          <Button type="submit" colorScheme="blue" mt={10} width="full">
            Entrar
          </Button>
        </form>
        <NextLink href="/cadastro" passHref>
          <Link mt={5}>Cadastrar</Link>
        </NextLink>
      </Flex>
    </Flex>
  )
}

export default Login
