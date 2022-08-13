type SignInRequestData = {
  username: string
  password: string
}

export async function signInRequest(data: SignInRequestData) {
  console.log(data)
  return {}
}
