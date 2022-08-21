# Succotash - Frontend

A melhor forma de pagar sua conta em bares e restaurantes

https://github.com/brenoferreiraufu/succotash


## Tabela de conteúdos

- [Estrutura](#estrutura)
- [Tecnologias](#tecnologias)

## Estrutura

    root
    ├── public                         (Entrypoint para a compilação do React)
    |     ├── images                   (Imagens)
    ├── src                            (Diretório principal dos arquivos da aplicação)
    |   ├── components                 (Componentes da aplicação)
    |   ├── contexts                   (Contexto da aplicação)
    |   ├── pages                      (Paginás iniciais / rotas)
    |   ├── services                   (Serviços externos [API])
    |   ├── styles                     (Estilos da aplicação)
    |   └── utils                      (Funções úteis)
    ├── .env.example                   (Exemplo da variaveis de ambiente utilizados na aplicação)
    ├── .env.local                     (Variaveis de ambiente)
    ├── README.md                      (breve definição)
    └── ...                            (Outros arquivos de configuração)

## Tecnologias

- [TypeScript](https://www.typescriptlang.org/)
- [NextJS](https://nextjs.org/)
- [Chakra UI](https://chakra-ui.com/)
- [Eslint](https://eslint.org/)
- [Prettier](https://prettier.io/)

## Montagem do ambiente de desenvolvimento

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Node.js](https://nodejs.org/en/), [Yarn](https://classic.yarnpkg.com/lang/en/).
Além disto é bom ter um editor para trabalhar com o código como [VSCode](https://code.visualstudio.com/)

**Clonar projeto:**

- *https*

```sh
git clone https://github.com/brenoferreiraufu/succotash.git
```

- *ssh*

```sh
git clone git@github.com:brenoferreiraufu/succotash.git
```

**Instalar dependencias:**

```sh
yarn
```

**Configurar variáveis de ambiente:**

```sh
touch .env.local
nano .env.local
```

**Iniciar o projeto:**

```sh
yarn dev
