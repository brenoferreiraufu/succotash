export const moneyFormat = (value: string | number) => {
  const numberFormat = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })

  return numberFormat.format(Number(value))
}
